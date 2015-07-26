package cacoethes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TrackController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Track.list(params), model:[trackCount: Track.count()]
    }

    def show(Track track) {
        respond track
    }

    def create() {
        respond new Track(params)
    }

    @Transactional
    def save(Track track) {
        if (track == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (track.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond track.errors, view:'create'
            return
        }

        track.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'track.label', default: 'Track'), track.id])
                redirect track
            }
            '*' { respond track, [status: CREATED] }
        }
    }

    def edit(Track track) {
        respond track
    }

    @Transactional
    def update(Track track) {
        if (track == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (track.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond track.errors, view:'edit'
            return
        }

        track.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'track.label', default: 'Track'), track.id])
                redirect track
            }
            '*'{ respond track, [status: OK] }
        }
    }

    @Transactional
    def delete(Track track) {

        if (track == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        track.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'track.label', default: 'Track'), track.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'track.label', default: 'Track'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
