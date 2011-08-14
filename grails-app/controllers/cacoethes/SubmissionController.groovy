package cacoethes

import cacoethes.auth.User
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

@Secured("ROLE_USER")
class SubmissionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
        if (SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            submissions = Submission.list(params)
            submissionCount = Submission.count()
        }
        else {
            submissions = Submission.findAllByUser(springSecurityService.currentUser, params)
            submissionCount = Submission.countByUser(springSecurityService.currentUser)
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

        if (!submissionInstance.save(flush: true)) {
            render view: "create", model: [submissionInstance: submissionInstance]
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'submission.label', default: 'Submission'), submissionInstance.id])
        redirect action: "list"
    }

    def show() {
        def submissionInstance = Submission.get(params.id)

        if (!submissionInstance || !checkSubmissionAccess(submissionInstance)) {
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

        [submissionInstance: submissionInstance]
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

        bindData submissionInstance, params, ["accepted", "schedule"]
        submissionInstance.user = springSecurityService.currentUser

        if (!submissionInstance.save(flush: true)) {
            render(view: "edit", model: [submissionInstance: submissionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'submission.label', default: 'Submission'), submissionInstance.id])
        redirect(action: "show", id: submissionInstance.id)
    }

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

    /**
     * Checks whether the current user has permission to access the given submission.
     */
    private checkSubmissionAccess(submissionInstance) {
        // Check that the user has permission to view this one.
        return SpringSecurityUtils.ifAllGranted("ROLE_ADMIN") || submissionInstance.user == springSecurityService.currentUser
    }
}
