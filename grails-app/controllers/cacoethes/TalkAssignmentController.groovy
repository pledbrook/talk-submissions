package cacoethes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TalkAssignmentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TalkAssignment.list(params), model:[talkAssignmentCount: TalkAssignment.count()]
    }

    def show(TalkAssignment talkAssignment) {
        respond talkAssignment
    }

    def create() {
        respond new TalkAssignment(params)
    }

    @Transactional
    def save(TalkAssignment talkAssignment) {
        if (talkAssignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (talkAssignment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond talkAssignment.errors, view:'create'
            return
        }

        talkAssignment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'talkAssignment.label', default: 'TalkAssignment'), talkAssignment.id])
                redirect talkAssignment
            }
            '*' { respond talkAssignment, [status: CREATED] }
        }
    }

    def edit(TalkAssignment talkAssignment) {
        respond talkAssignment
    }

    @Transactional
    def update(TalkAssignment talkAssignment) {
        if (talkAssignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (talkAssignment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond talkAssignment.errors, view:'edit'
            return
        }

        talkAssignment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'talkAssignment.label', default: 'TalkAssignment'), talkAssignment.id])
                redirect talkAssignment
            }
            '*'{ respond talkAssignment, [status: OK] }
        }
    }

    @Transactional
    def delete(TalkAssignment talkAssignment) {

        if (talkAssignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        talkAssignment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'talkAssignment.label', default: 'TalkAssignment'), talkAssignment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'talkAssignment.label', default: 'TalkAssignment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
