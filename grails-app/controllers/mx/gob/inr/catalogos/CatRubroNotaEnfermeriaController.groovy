package mx.gob.inr.catalogos

import org.springframework.dao.DataIntegrityViolationException

class CatRubroNotaEnfermeriaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        //params.max = Math.min(max ?: 10, 100)
        //[catRubroNotaEnfermeriaInstanceList: CatRubroNotaEnfermeria.list(params), catRubroNotaEnfermeriaInstanceTotal: CatRubroNotaEnfermeria.count()]
		
		if(!params.max) params.max = 10
		
				if(params?.format && params.format != "html"){
					response.contentType = grailsApplication.config.grails.mime.types[params.format]
					response.setHeader("Content-disposition", "attachment; filename=catrubronota.${params.extension}")
		
		exportService.export(params.format, response.outputStream,CatRubroNotaEnfermeria.list(params), [:], [:])
				}
				
		[catRubroNotaEnfermeriaInstanceList: CatRubroNotaEnfermeria.list(params), catRubroNotaEnfermeriaInstanceTotal: CatRubroNotaEnfermeria.count()]
    }

    def create() {
        [catRubroNotaEnfermeriaInstance: new CatRubroNotaEnfermeria(params)]
    }

    def save() {
        def catRubroNotaEnfermeriaInstance = new CatRubroNotaEnfermeria(params)
        if (!catRubroNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "create", model: [catRubroNotaEnfermeriaInstance: catRubroNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), catRubroNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catRubroNotaEnfermeriaInstance.id)
    }

    def show(Long id) {
        def catRubroNotaEnfermeriaInstance = CatRubroNotaEnfermeria.get(id)
        if (!catRubroNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catRubroNotaEnfermeriaInstance: catRubroNotaEnfermeriaInstance]
    }

    def edit(Long id) {
        def catRubroNotaEnfermeriaInstance = CatRubroNotaEnfermeria.get(id)
        if (!catRubroNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        [catRubroNotaEnfermeriaInstance: catRubroNotaEnfermeriaInstance]
    }

    def update(Long id, Long version) {
        def catRubroNotaEnfermeriaInstance = CatRubroNotaEnfermeria.get(id)
        if (!catRubroNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (catRubroNotaEnfermeriaInstance.version > version) {
                catRubroNotaEnfermeriaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria')] as Object[],
                          "Another user has updated this CatRubroNotaEnfermeria while you were editing")
                render(view: "edit", model: [catRubroNotaEnfermeriaInstance: catRubroNotaEnfermeriaInstance])
                return
            }
        }

        catRubroNotaEnfermeriaInstance.properties = params

        if (!catRubroNotaEnfermeriaInstance.save(flush: true)) {
            render(view: "edit", model: [catRubroNotaEnfermeriaInstance: catRubroNotaEnfermeriaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), catRubroNotaEnfermeriaInstance.id])
        redirect(action: "show", id: catRubroNotaEnfermeriaInstance.id)
    }

    def delete(Long id) {
        def catRubroNotaEnfermeriaInstance = CatRubroNotaEnfermeria.get(id)
        if (!catRubroNotaEnfermeriaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "list")
            return
        }

        try {
            catRubroNotaEnfermeriaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria'), id])
            redirect(action: "show", id: id)
        }
    }
}
