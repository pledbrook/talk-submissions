package cacoethes

import cacoethes.auth.User

class Submission {
    String title
    String summary
    TalkCategory category
    Boolean accepted
    Integer year
    Date schedule

    static hasOne = [assignment: TalkAssignment]
    static hasMany = [audiences: Audience]

    static belongsTo = [user: User]
    static transients = []

    static constraints = {
        title blank: false
        summary blank: false, maxSize: 2000
        audiences minSize: 1
        category()
        accepted nullable: true
        schedule nullable: true
        assignment nullable: true
    }

    static mapping = {
        audiences lazy: false
        category lazy: false
    }
}
