package mx.gob.inr.catalogos

import org.springframework.dao.DataIntegrityViolationException

class CatSegmentoNotaEnfermeriaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //[catSegmentoNotaEnfermeriaInstanceList: CatSegmentoNotaEnfermeria.list(params), catSegmentoNotaEnfermeriaInstanceTotal: CatSegmentoNotaEnfermeria.count()]
    
		if(!params.max) params.max = 10
		
				if(params?.format && params.format != "html"){
					response.contentType = grailsApplication.config.grails.mime.types[params.format]
					response.setHeader("Content-disposition", "attachment; filename=catsegmentonota.${params.extension}")
		
		exportService.export(params.format, response.outputStream,CatSegmentoNotaEnfermeria.list(params), [:], [:])
				}
				[catSegmentoNotaEnfermeriaInstanceList: CatSegmentoNotaEnfermeria.list(params), catSegmentoNotaEnfermeriaInstanceTotal: CatSegmentoNotaEnfermeria.count()]
		
	}

    def create() {
        [catSegmentoNotaEnfermeriaInstance: new CatSegmentoNotaEnfermeria(params)]
    }

    def save() {
        def catSegmentoNotaEnfermeriaInstance = new CatSegmentoNotaEnfermeria(params)
        if (!catSegmentoNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "create", model: [catSegmentoNotaEnfermeriaInstance: catSegmentoNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), catSegmentoNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catSegmentoNotaEnfermeriaInstance.id)
    }

    def show(Long id) {
        def catSegmentoNotaEnfermeriaInstance = CatSegmentoNotaEnfermeria.get(id)
        if (!catSegmentoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catSegmentoNotaEnfermeriaInstance: catSegmentoNotaEnfermeriaInstance]
    }

    def edit(Long id) {
        def catSegmentoNotaEnfermeriaInstance = CatSegmentoNotaEnfermeria.get(id)
        if (!catSegmentoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catSegmentoNotaEnfermeriaInstance: catSegmentoNotaEnfermeriaInstance]
    }

    def update(Long id, Long version) {
        def catSegmentoNotaEnfermeriaInstance = CatSegmentoNotaEnfermeria.get(id)
        if (!catSegmentoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (catSegmentoNotaEnfermeriaInstance.version > version) {
                catSegmentoNotaEnfermeriaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria')] as Object[],
                          "Another user has updated this CatSegmentoNotaEnfermeria while you were editing")
                render(view: "edit", model: [catSegmentoNotaEnfermeriaInstance: catSegmentoNotaEnfermeriaInstance])
                return
            }
        }

        catSegmentoNotaEnfermeriaInstance.properties = params

        if (!catSegmentoNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "edit", model: [catSegmentoNotaEnfermeriaInstance: catSegmentoNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), catSegmentoNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catSegmentoNotaEnfermeriaInstance.id)
    }

    def delete(Long id) {
        def catSegmentoNotaEnfermeriaInstance = CatSegmentoNotaEnfermeria.get(id)
        if (!catSegmentoNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        try {
            catSegmentoNotaEnfermeriaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria'), id])
            redirect(action: "show", id: id)
        }
    }
}
