package cacoethes

class TimeSlot {
    Integer day
    Integer order
    Date startTime
    Date endTime

    static constraints = {
        day min: 1
        order min: 1
        startTime()
        endTime()
    }

    static mapping = {
        order column: "slot_position"
    }

    String toString() { "Day ${day} - slot ${order}" }
}
