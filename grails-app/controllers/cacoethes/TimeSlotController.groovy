package cacoethes

import grails.plugins.springsecurity.Secured

@Secured("ROLE_ADMIN")
class TimeSlotController {
    static scaffold = true
}
