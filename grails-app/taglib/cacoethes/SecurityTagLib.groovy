package cacoethes

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class SecurityTagLib {
    static namespace = "app"

    def springSecurityService

    /**
     * Determines whether the current user has permission to edit a given talk
     * submission. Outputs the body of the tag only if the user is either an
     * administrator or is the submitter.
     * @attr id REQUIRED The ID of the submission.
     */
    def canEditSubmission = { attrs, body ->
        if (attrs.id == null) throwTagError "Tag [canEditSubmission] requires the [id] attribute"

        if (checkSubmissionAccess(attrs.id)) {
            out << body()
        }
    }

    private checkSubmissionAccess(Long submissionId) {
        def submission = Submission.get(submissionId)

        // Check that the user has permission to view this one.
        return SpringSecurityUtils.ifAllGranted("ROLE_ADMIN") ||
                submission?.user == springSecurityService.currentUser
    }
}
