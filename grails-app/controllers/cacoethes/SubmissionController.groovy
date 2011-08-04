package cacoethes

import grails.plugins.springsecurity.Secured

@Secured("ROLE_USER")
class SubmissionController {
    static scaffold = true

    @Secured("ROLE_ADMIN")
    def showAll() {
        render "Show all submissions page"
    }
}
