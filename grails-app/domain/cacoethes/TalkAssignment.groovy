package cacoethes

class TalkAssignment {
    Track track
    TimeSlot slot

    static belongsTo = [talk: Submission]

    static constraints = {
        talk()
        track()
        slot()
    }
}
