package mx.gob.inr.hojaRegistroClinico

import org.springframework.dao.DataIntegrityViolationException

class HojaRegistroEnfermeriaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //[hojaRegistroEnfermeriaInstanceList: HojaRegistroEnfermeria.list(params), hojaRegistroEnfermeriaInstanceTotal: HojaRegistroEnfermeria.count()]
    
		if(!params.max) params.max = 10
		
				if(params?.format && params.format != "html"){
					response.contentType = grailsApplication.config.grails.mime.types[params.format]
					response.setHeader("Content-disposition", "attachment; filename=hojaregistroenfermeria.${params.extension}")
		
		exportService.export(params.format, response.outputStream,HojaRegistroEnfermeria.list(params), [:], [:])
				}
		[hojaRegistroEnfermeriaInstanceList: HojaRegistroEnfermeria.list(params), hojaRegistroEnfermeriaInstanceTotal: HojaRegistroEnfermeria.count()]
	}

    def create() {
        [hojaRegistroEnfermeriaInstance: new HojaRegistroEnfermeria(params)]
    }

    def save() {
        def hojaRegistroEnfermeriaInstance = new HojaRegistroEnfermeria(params)
        if (!hojaRegistroEnfermeriaInstance.save(flush: true)) {
            render(view: "create", model: [hojaRegistroEnfermeriaInstance: hojaRegistroEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), hojaRegistroEnfermeriaInstance.id])
        redirect(action: "show", id: hojaRegistroEnfermeriaInstance.id)
    }

    def show(Long id) {
        def hojaRegistroEnfermeriaInstance = HojaRegistroEnfermeria.get(id)
        if (!hojaRegistroEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [hojaRegistroEnfermeriaInstance: hojaRegistroEnfermeriaInstance]
    }

    def edit(Long id) {
        def hojaRegistroEnfermeriaInstance = HojaRegistroEnfermeria.get(id)
        if (!hojaRegistroEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [hojaRegistroEnfermeriaInstance: hojaRegistroEnfermeriaInstance]
    }

    def update(Long id, Long version) {
        def hojaRegistroEnfermeriaInstance = HojaRegistroEnfermeria.get(id)
        if (!hojaRegistroEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hojaRegistroEnfermeriaInstance.version > version) {
                hojaRegistroEnfermeriaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria')] as Object[],
                          "Another user has updated this HojaRegistroEnfermeria while you were editing")
                render(view: "edit", model: [hojaRegistroEnfermeriaInstance: hojaRegistroEnfermeriaInstance])
                return
            }
        }

        hojaRegistroEnfermeriaInstance.properties = params

        if (!hojaRegistroEnfermeriaInstance.save(flush: true)) {
            render(view: "edit", model: [hojaRegistroEnfermeriaInstance: hojaRegistroEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), hojaRegistroEnfermeriaInstance.id])
        redirect(action: "show", id: hojaRegistroEnfermeriaInstance.id)
    }

    def delete(Long id) {
        def hojaRegistroEnfermeriaInstance = HojaRegistroEnfermeria.get(id)
        if (!hojaRegistroEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "list")
            return
        }

        try {
            hojaRegistroEnfermeriaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria'), id])
            redirect(action: "show", id: id)
        }
    }
}
