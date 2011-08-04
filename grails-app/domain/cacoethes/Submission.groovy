package cacoethes

import cacoethes.auth.User

class Submission {
    String title
    String summary

    static belongsTo = [user: User]

    static constraints = {
        title blank: false
        summary blank: false, maxSize: 1000
    }
}
