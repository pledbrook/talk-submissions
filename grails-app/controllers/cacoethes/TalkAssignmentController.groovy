package cacoethes

import grails.plugins.springsecurity.Secured

@Secured("ROLE_ADMIN")
class TalkAssignmentController {
    static scaffold = true
}
