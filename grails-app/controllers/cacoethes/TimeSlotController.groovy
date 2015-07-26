package cacoethes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TimeSlotController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TimeSlot.list(params), model:[timeSlotCount: TimeSlot.count()]
    }

    def show(TimeSlot timeSlot) {
        respond timeSlot
    }

    def create() {
        respond new TimeSlot(params)
    }

    @Transactional
    def save(TimeSlot timeSlot) {
        if (timeSlot == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (timeSlot.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond timeSlot.errors, view:'create'
            return
        }

        timeSlot.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'timeSlot.label', default: 'TimeSlot'), timeSlot.id])
                redirect timeSlot
            }
            '*' { respond timeSlot, [status: CREATED] }
        }
    }

    def edit(TimeSlot timeSlot) {
        respond timeSlot
    }

    @Transactional
    def update(TimeSlot timeSlot) {
        if (timeSlot == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (timeSlot.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond timeSlot.errors, view:'edit'
            return
        }

        timeSlot.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'timeSlot.label', default: 'TimeSlot'), timeSlot.id])
                redirect timeSlot
            }
            '*'{ respond timeSlot, [status: OK] }
        }
    }

    @Transactional
    def delete(TimeSlot timeSlot) {

        if (timeSlot == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        timeSlot.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'timeSlot.label', default: 'TimeSlot'), timeSlot.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'timeSlot.label', default: 'TimeSlot'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
