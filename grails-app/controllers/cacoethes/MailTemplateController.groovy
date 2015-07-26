package cacoethes

import static org.springframework.http.HttpStatus.*

import grails.transaction.Transactional

@Transactional(readOnly = true)
class MailTemplateController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MailTemplate.list(params), model:[mailTemplateCount: MailTemplate.count()]
    }

    def show(MailTemplate mailTemplate) {
        respond mailTemplate
    }

    def create() {
        respond new MailTemplate(params)
    }

    @Transactional
    def save(MailTemplate mailTemplate) {
        if (mailTemplate == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mailTemplate.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mailTemplate.errors, view:'create'
            return
        }

        mailTemplate.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mailTemplate.label', default: 'MailTemplate'), mailTemplate.id])
                redirect mailTemplate
            }
            '*' { respond mailTemplate, [status: CREATED] }
        }
    }

    def edit(MailTemplate mailTemplate) {
        respond mailTemplate
    }

    @Transactional
    def update(MailTemplate mailTemplate) {
        if (mailTemplate == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mailTemplate.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mailTemplate.errors, view:'edit'
            return
        }

        mailTemplate.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'mailTemplate.label', default: 'MailTemplate'), mailTemplate.id])
                redirect mailTemplate
            }
            '*'{ respond mailTemplate, [status: OK] }
        }
    }

    @Transactional
    def delete(MailTemplate mailTemplate) {

        if (mailTemplate == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        mailTemplate.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'mailTemplate.label', default: 'MailTemplate'), mailTemplate.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mailTemplate.label', default: 'MailTemplate'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
