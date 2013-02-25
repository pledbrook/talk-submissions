package cacoethes

import cacoethes.auth.User
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

@Secured("ROLE_USER")
class SubmissionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", sendStatusEmail: "POST"]

    def grailsApplication
    def sendGridService
    def springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        // Can't view submissions until a profile is created.
        if (SpringSecurityUtils.ifNotGranted("ROLE_ADMIN") && !springSecurityService.currentUser.profile) {
            redirect controller: "profile", action: "create"
            return
        }

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def submissions
        def submissionCount
        def year = currentYear
        if (SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            submissions = Submission.findAllByYear(year, params)
            submissionCount = Submission.countByYear(year)
        }
        else {
            submissions = Submission.findAllByUserAndYear(springSecurityService.currentUser, year, params)
            submissionCount = Submission.countByUserAndYear(springSecurityService.currentUser, year)
        }

        [submissionInstanceList: submissions, submissionInstanceTotal: submissionCount]
    }

    def create() {
        [submissionInstance: new Submission(params)]
    }

    def save() {
        def submissionInstance = new Submission()
        bindData submissionInstance, params, ["accepted", "schedule"]
        submissionInstance.user = springSecurityService.currentUser
        submissionInstance.year = new Date()[Calendar.YEAR]

        if (!submissionInstance.save(flush: true)) {
            render view: "create", model: [submissionInstance: submissionInstance]
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'submission.label', default: 'Submission'), submissionInstance.id])
        redirect action: "list"
    }

    def show() {
        def submissionInstance = Submission.get(params.id)

        if (!submissionInstance || (!submissionInstance.assignment && !checkSubmissionAccess(submissionInstance))) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "list")
            return
        }

        [submissionInstance: submissionInstance]
    }

    def edit() {
        def submissionInstance = Submission.get(params.id)

        if (!submissionInstance || !checkSubmissionAccess(submissionInstance)) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "list")
            return
        }

        [submissionInstance: submissionInstance,
         assignment: AssignmentCommand.fromSubmission(submissionInstance),
         allTracks: Track.list(sort: "id"),
         allSlots: TimeSlot.list(sort: "id")]
    }

    def update() {
        def submissionInstance = Submission.get(params.id)

        if (!submissionInstance || !checkSubmissionAccess(submissionInstance)) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (submissionInstance.version > version) {
                submissionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'submission.label', default: 'Submission')] as Object[],
                          "Another user has updated this Submission while you were editing")
                render(view: "edit", model: [submissionInstance: submissionInstance])
                return
            }
        }

        bindData submissionInstance, params, ["accepted", "schedule", "user"]

        updateTalkAssignment submissionInstance, params.trackId, params.slotId

        if (params.accepted == "undecided") {
            submissionInstance.accepted = null
        }
        else {
            submissionInstance.accepted = Boolean.valueOf(params.accepted)
        }

        if (!submissionInstance.save(flush: true)) {
            render view: "edit", model: [
                    submissionInstance: submissionInstance,
                    assignment: AssignmentCommand.fromSubmission(submissionInstance),
                    allTracks: Track.list(sort: "id"),
                    allSlots: TimeSlot.list(sort: "id")]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'submission.label', default: 'Submission'), submissionInstance.id])
        redirect(action: "show", id: submissionInstance.id)
    }

    @Secured("ROLE_ADMIN")
    def delete() {
        def submissionInstance = Submission.get(params.id)
        if (!submissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "list")
            return
        }

        try {
            submissionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def schedule(Integer forYear) {
        if (!forYear) forYear = currentYear
        def talks = TalkAssignment.where { talk.year == forYear }.list()

        def schedule = createSchedule(talks)
        [schedule: schedule, year: forYear, trackNames: Track.list(sort: "id")*.name]
    }

    @Secured("ROLE_ADMIN")
    def conflicts() {
        def talks = TalkAssignment.where { talk.year == currentYear }.list()
        def conflicts = talks.groupBy { it.track?.name + "_" + it.slot?.toString() }.findAll { key, value -> value?.size() > 1 }

        render {
            ul {
                for (conflict in conflicts) {
                    li confict.key + ": " + conflict.value*.talk*.title.join(', ')
                }
            }
        }
    }

    @Secured("ROLE_ADMIN")
    def sendStatusEmail(Long id) {
        def submissionInstance = Submission.get(id)
        if (!submissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'submission.label', default: 'Submission'), params.id])
            redirect action: "show", params: [id: id]
            return
        }

        // Can't send an email for a submission that is still pending.
        if (submissionInstance.accepted == null) {
            // Status is still pending, so cannot send an email.
            flash.message = message(code: 'message.submission.pending')
            redirect action: "show", params: [id: id]
            return
        }
        
        // Pick the appropriate email template based on the status.
        def emailTemplate = MailTemplate.where { key == (submissionInstance.accepted ? "accepted" : "rejected") }.get()
        def binding = createTemplateBinding(submissionInstance)
        def engine = new groovy.text.SimpleTemplateEngine()

        def config = grailsApplication.config
        sendGridService.sendMail {
            from config.grails.mail.default.from
            to engine.createTemplate(emailTemplate.to).make(binding).toString()

            if (emailTemplate.cc) {
                cc engine.createTemplate(emailTemplate.cc).make(binding).toString()
            }

            if (emailTemplate.bcc) {
                bcc engine.createTemplate(emailTemplate.bcc).make(binding).toString()
            }

            subject engine.createTemplate(emailTemplate.subject).make(binding).toString()
            body engine.createTemplate(emailTemplate.body).make(binding).toString()
        }

        flash.message = message(code: 'message.submission.emailSent')
        redirect action: "show", params: [id: id]
    }

    private createTemplateBinding(submission) {
        def profile = submission.user.profile
        return [email: profile.email, name: profile.name, title: submission.title]
    }

    /**
     * Checks whether the current user has permission to access the given submission.
     */
    private checkSubmissionAccess(submissionInstance) {
        // Check that the user has permission to view this one.
        return SpringSecurityUtils.ifAllGranted("ROLE_ADMIN") ||
                submissionInstance.user == springSecurityService.currentUser
    }

    private updateTalkAssignment(submission, trackId, slotId) {
        if (!trackId || !slotId) {
            submission.assignment = null
            return
        }

        def assignment = submission.assignment
        if (!assignment) {
            assignment = new TalkAssignment(talk: submission, track: Track.get(trackId), slot: TimeSlot.get(slotId))
            submission.assignment = assignment
        }
        else {
            if (assignment.track?.id != trackId) assignment.track = Track.get(trackId)
            if (assignment.slot?.id != slotId) assignment.slot = TimeSlot.get(slotId)
        }

        return assignment
    }

    private createSchedule(List talks) {
        def numTracks = Track.count()
        def days = talks.groupBy { it.slot.day }.sort()
        for (day in days) {
            day.value = day.value.groupBy { it.slot?.order }.sort()

            for (slot in day.value) {
                def slotTalks = (0..<numTracks).collect { null }
                for (t in slot.value) {
                    slotTalks[t.track.order - 1] = t
                }
                slot.value = slotTalks
            }
        }

        return days
    }

    protected int getCurrentYear() { new Date()[Calendar.YEAR] }
}

class AssignmentCommand {
    Long trackId
    Long slotId

    static constraints = {
        trackId nullable: true
        slotId nullable: true
    }

    static fromSubmission(submission) {
        return new AssignmentCommand(
                trackId: submission.assignment?.track?.id,
                slotId: submission.assignment?.slot?.id)
    }
}
