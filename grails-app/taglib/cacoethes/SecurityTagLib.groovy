package cacoethes

import org.pac4j.springframework.security.authentication.ClientAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder as SCH

class SecurityTagLib {
    static namespace = "app"

    def userService

    /**
     * @attr name REQUIRED The name of the required role.
     */
    def loggedIn = { attrs, body ->
        if (SCH.context.authentication instanceof ClientAuthenticationToken) {
            out << body()
        }
    }

    /**
     * @attr name REQUIRED The name of the required role.
     */
    def hasRole = { attrs, body ->
        if (attrs.name == null) throwTagError "Tag [hasRole] requires the [name] attribute"

        if (request.isUserInRole(attrs.name)) {
            out << body()
        }
    }

    /**
     * @attr name REQUIRED The name of the required role.
     */
    def notHasRole = { attrs, body ->
        if (attrs.name == null) throwTagError "Tag [notHasRole] requires the [name] attribute"

        if (!request.isUserInRole(attrs.name)) {
            out << body()
        }
    }

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
        // Check that the user has permission to view this one.
        return request.isUserInRole("ROLE_ADMIN") ||
                Submission.get(submissionId)?.user?.id == currentUserId
    }

    private getCurrentUserId() {
        return userService.currentUser()?.id
    }
}
