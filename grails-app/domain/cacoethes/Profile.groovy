package cacoethes

import cacoethes.auth.User

class Profile {
    String name
    String email
    String bio
    Boolean needTravel
    Boolean needAccommodation
    String travelFrom

    static belongsTo = [user: User]

    static constraints = {
        name blank: false
        email blank: false
        bio maxSize: 2000
        needTravel nullable: true
        needAccommodation nullable: true
        travelFrom nullable: true
    }
}
