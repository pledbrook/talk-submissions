package cacoethes

class TalkCategory {
    String name
    Integer durationInMinutes

    static constraints = {
        name blank: false
        durationInMinutes()
    }
}
