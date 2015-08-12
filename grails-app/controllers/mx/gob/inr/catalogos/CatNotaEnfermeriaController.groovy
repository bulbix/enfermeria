package mx.gob.inr.catalogos

import org.springframework.dao.DataIntegrityViolationException

class CatNotaEnfermeriaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //[catNotaEnfermeriaInstanceList: CatNotaEnfermeria.list(params), catNotaEnfermeriaInstanceTotal: CatNotaEnfermeria.count()]
		
		if(!params.max) params.max = 10
		
		if(params?.format && params.format != "html"){
			response.contentType = grailsApplication.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=catnotaenfermeria.${params.extension}")
		
			exportService.export(params.format, response.outputStream,CatNotaEnfermeria.list(params), [:], [:])
		}
		
		[catNotaEnfermeriaInstanceList: CatNotaEnfermeria.list(params), catNotaEnfermeriaInstanceTotal: CatNotaEnfermeria.count()]
	}

    def create() {
        [catNotaEnfermeriaInstance: new CatNotaEnfermeria(params)]
    }

    def save() {
        def catNotaEnfermeriaInstance = new CatNotaEnfermeria(params)
        if (!catNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "create", model: [catNotaEnfermeriaInstance: catNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), catNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catNotaEnfermeriaInstance.id)
    }

    def show(Long id) {
        def catNotaEnfermeriaInstance = CatNotaEnfermeria.get(id)
        if (!catNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catNotaEnfermeriaInstance: catNotaEnfermeriaInstance]
    }

    def edit(Long id) {
        def catNotaEnfermeriaInstance = CatNotaEnfermeria.get(id)
        if (!catNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catNotaEnfermeriaInstance: catNotaEnfermeriaInstance]
    }

    def update(Long id, Long version) {
        def catNotaEnfermeriaInstance = CatNotaEnfermeria.get(id)
        if (!catNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (catNotaEnfermeriaInstance.version > version) {
                catNotaEnfermeriaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria')] as Object[],
                          "Another user has updated this CatNotaEnfermeria while you were editing")
                render(view: "edit", model: [catNotaEnfermeriaInstance: catNotaEnfermeriaInstance])
                return
            }
        }

        catNotaEnfermeriaInstance.properties = params

        if (!catNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "edit", model: [catNotaEnfermeriaInstance: catNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), catNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catNotaEnfermeriaInstance.id)
    }

    def delete(Long id) {
        def catNotaEnfermeriaInstance = CatNotaEnfermeria.get(id)
        if (!catNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        try {
            catNotaEnfermeriaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'catNotaEnfermeria.label', default: 'CatNotaEnfermeria'), id])
            redirect(action: "show", id: id)
        }
    }
}
