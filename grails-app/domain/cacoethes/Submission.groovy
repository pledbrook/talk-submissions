package cacoethes

import cacoethes.auth.User

class Submission {
    String title
    String summary
    Boolean accepted
    Integer year
    Date schedule

    static hasOne = [assignment: TalkAssignment]

    static belongsTo = [user: User]

    static constraints = {
        title blank: false
        summary blank: false, maxSize: 1000
        accepted nullable: true
        schedule nullable: true
        assignment nullable: true
    }
}
