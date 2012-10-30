package cacoethes

class MailTemplate {
    String key
    String to
    String cc
    String bcc
    String subject
    String body

    static constraints = {
        key     blank: false
        to      nullable: true
        cc      nullable: true
        bcc     nullable: true
        subject blank: false
        body    blank: false, maxSize: 50000
    }

    static mapping = {
        key column: "template_key"
        to column: "to_address"
        cc column: "cc_address"
        bcc column: "bcc_address"
    }
}
