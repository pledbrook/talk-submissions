package cacoethes

class Audience {
    String experience
    Integer orderIndex

    static constraints = {
        experience blank: false
        orderIndex()
    }
}
