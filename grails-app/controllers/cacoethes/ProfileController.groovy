package cacoethes

import cacoethes.auth.User
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.context.SecurityContextHolder as SCH

class ProfileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def userService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [profileInstanceList: Profile.list(params), profileInstanceTotal: Profile.count()]
    }

    def acceptedEmails(Integer forYear) {
        def acceptedTalks = Submission.where {
            accepted == true
            if (forYear) {
                year == forYear
            }
        }.list()

        def profiles = acceptedTalks.collect { it.user.profile }.unique()
        render profiles*.email.join(", ")
    }

    def submittedEmails(Integer forYear) {
        def submittedTalks = Submission.where {
            if (forYear) {
                year == forYear
            }
        }.property("user").list()

        def profiles = submittedTalks*.profile.unique()
        render profiles*.email.join(", ")
    }

    def acceptedExpenses(Integer forYear) {
        def acceptedTalks = Submission.where {
            accepted == true
            if (forYear) {
                year == forYear
            }
        }.list()

        def profiles = acceptedTalks.collect { it.user.profile }.unique()
        profiles = profiles.findAll { it.needTravel || it.needAccommodation }

        [profiles: profiles]
    }

    def create() {
        // If a profile already exists, go to the edit view.
        def user = userService.currentUser()
        if (!hasRole("ROLE_ADMIN") && user.profile) {
            redirect action: "edit", id: user.profile.id
            return
        }

        [profileInstance: new Profile(params)]
    }

    def save() {
        final profileInstance = new Profile(params)
        final currentUser = userService.currentUser()
        if (currentUser.username != "admin") profileInstance.user = currentUser
        if (!profileInstance.save(flush: true)) {
            render(view: "create", model: [profileInstance: profileInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'profile.label', default: 'Profile'), profileInstance.id])
        redirect uri: "/"
    }

    def show() {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance || !checkProfileAccess(profileInstance)) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "list")
            return
        }

        [profileInstance: profileInstance]
    }

    def edit() {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance || !checkProfileAccess(profileInstance)) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "list")
            return
        }

        [profileInstance: profileInstance]
    }

    def update() {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance || !checkProfileAccess(profileInstance)) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (profileInstance.version > version) {
                profileInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'profile.label', default: 'Profile')] as Object[],
                          "Another user has updated this Profile while you were editing")
                render(view: "edit", model: [profileInstance: profileInstance])
                return
            }
        }

        profileInstance.properties = params

        if (!profileInstance.save(flush: true)) {
            render(view: "edit", model: [profileInstance: profileInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'profile.label', default: 'Profile'), profileInstance.id])
        redirect(action: "show", id: profileInstance.id)
    }

    def delete() {
        def profileInstance = Profile.get(params.id)
        if (!profileInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "list")
            return
        }

        try {
            profileInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'profile.label', default: 'Profile'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def loggedIn() {
        [currentUser: userService.currentUser()]
    }

    /**
     * Checks whether the current user has permission to access the given profile.
     */
    private checkProfileAccess(profile) {
        // Check that the user has permission to view this one.
        return hasRole("ROLE_ADMIN") || profile.user == userService.currentUser()
    }

    private boolean hasRole(String role) {
        return SCH.context?.authentication?.authorities*.authority.contains(role)
    }

    private User getCurrentUser() {
        return User.get(SCH.context?.authentication?.principal?.id)
    }
}
