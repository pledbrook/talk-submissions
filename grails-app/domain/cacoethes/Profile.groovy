package cacoethes

import cacoethes.auth.User

class Profile {
    String name
    String email
    String bio
    String twitterId
    String organization
    Boolean needTravel
    Boolean needAccommodation
    String travelFrom
    String notes

    static belongsTo = [user: User]

    static constraints = {
        name blank: false
        email blank: false
        twitterId nullable: true
        organization nullable: true
        bio blank: false, maxSize: 2000
        needTravel nullable: true
        needAccommodation nullable: true
        travelFrom blank: false
        notes nullable: true, maxSize: 2000
    }
}
