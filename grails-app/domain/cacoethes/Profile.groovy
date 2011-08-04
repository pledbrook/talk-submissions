package cacoethes

import cacoethes.auth.User

class Profile {
    String name
    String email
    String bio

    static belongsTo = [user: User]

    static constraints = {
        name blank: false
        email blank: false
        bio maxSize: 2000
    }
}
