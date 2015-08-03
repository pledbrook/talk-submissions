package cacoethes

import cacoethes.auth.User

class TalkRating {
    Integer rating

    static belongsTo = [reviewer: User, submission: Submission]

    static constraints = {
        rating range: 1..3
    }
}
