package mx.gob.inr.catalogos

import org.springframework.dao.DataIntegrityViolationException

class CatProcedimientoNotaEnfermeriaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //[catProcedimientoNotaEnfermeriaInstanceList: CatProcedimientoNotaEnfermeria.list(params), catProcedimientoNotaEnfermeriaInstanceTotal: CatProcedimientoNotaEnfermeria.count()]
    
		if(!params.max) params.max = 10
		
				if(params?.format && params.format != "html"){
					response.contentType = grailsApplication.config.grails.mime.types[params.format]
					response.setHeader("Content-disposition", "attachment; filename=catprocedimientonota.${params.extension}")
		
		exportService.export(params.format, response.outputStream,CatProcedimientoNotaEnfermeria.list(params), [:], [:])
				}
		
				[catProcedimientoNotaEnfermeriaInstanceList: CatProcedimientoNotaEnfermeria.list(params), catProcedimientoNotaEnfermeriaInstanceTotal: CatProcedimientoNotaEnfermeria.count()]
	}

    def create() {
        [catProcedimientoNotaEnfermeriaInstance: new CatProcedimientoNotaEnfermeria(params)]
    }

    def save() {
        def catProcedimientoNotaEnfermeriaInstance = new CatProcedimientoNotaEnfermeria(params)
        if (!catProcedimientoNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "create", model: [catProcedimientoNotaEnfermeriaInstance: catProcedimientoNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), catProcedimientoNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catProcedimientoNotaEnfermeriaInstance.id)
    }

    def show(Long id) {
        def catProcedimientoNotaEnfermeriaInstance = CatProcedimientoNotaEnfermeria.get(id)
        if (!catProcedimientoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catProcedimientoNotaEnfermeriaInstance: catProcedimientoNotaEnfermeriaInstance]
    }

    def edit(Long id) {
        def catProcedimientoNotaEnfermeriaInstance = CatProcedimientoNotaEnfermeria.get(id)
        if (!catProcedimientoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catProcedimientoNotaEnfermeriaInstance: catProcedimientoNotaEnfermeriaInstance]
    }

    def update(Long id, Long version) {
        def catProcedimientoNotaEnfermeriaInstance = CatProcedimientoNotaEnfermeria.get(id)
        if (!catProcedimientoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (catProcedimientoNotaEnfermeriaInstance.version > version) {
                catProcedimientoNotaEnfermeriaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria')] as Object[],
                          "Another user has updated this CatProcedimientoNotaEnfermeria while you were editing")
                render(view: "edit", model: [catProcedimientoNotaEnfermeriaInstance: catProcedimientoNotaEnfermeriaInstance])
                return
            }
        }

        catProcedimientoNotaEnfermeriaInstance.properties = params

        if (!catProcedimientoNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "edit", model: [catProcedimientoNotaEnfermeriaInstance: catProcedimientoNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), catProcedimientoNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catProcedimientoNotaEnfermeriaInstance.id)
    }

    def delete(Long id) {
        def catProcedimientoNotaEnfermeriaInstance = CatProcedimientoNotaEnfermeria.get(id)
        if (!catProcedimientoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        try {
            catProcedimientoNotaEnfermeriaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria'), id])
            redirect(action: "show", id: id)
        }
    }
}
