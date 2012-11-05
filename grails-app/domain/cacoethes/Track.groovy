package cacoethes

class Track {
    String name
    String location
    Integer order

    static constraints = {
        name blank: false
        location nullable: true
        order min: 1
    }

    static mapping = {
        order column: "track_position"
    }
}
