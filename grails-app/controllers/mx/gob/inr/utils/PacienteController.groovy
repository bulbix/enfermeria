package mx.gob.inr.utils

import org.springframework.dao.DataIntegrityViolationException

class PacienteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def exportService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        [pacienteInstanceList: Paciente.list(params), pacienteInstanceTotal: Paciente.count()]
		
		if(!params.max) params.max = 10
		
				if(params?.format && params.format != "html"){
					response.contentType = grailsApplication.config.grails.mime.types[params.format]
					response.setHeader("Content-disposition", "attachment; filename=pacientes.${params.extension}")
		
		exportService.export(params.format, response.outputStream,Paciente.list(params), [:], [:])
				}
		
				[pacienteInstanceList: Paciente.list(params), pacienteInstanceTotal: Paciente.count()]
    }

    def create() {
        [pacienteInstance: new Paciente(params)]
    }

    def save() {
        def pacienteInstance = new Paciente(params)
        if (!pacienteInstance.save(flush: true)) {
            render(view: "create", model: [pacienteInstance: pacienteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'paciente.label', default: 'Paciente'), pacienteInstance.id])
        redirect(action: "show", id: pacienteInstance.id)
    }

    def show(Long id) {
        def pacienteInstance = Paciente.get(id)
        if (!pacienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "list")
            return
        }

        [pacienteInstance: pacienteInstance]
    }

    def edit(Long id) {
        def pacienteInstance = Paciente.get(id)
        if (!pacienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "list")
            return
        }

        [pacienteInstance: pacienteInstance]
    }

    def update(Long id, Long version) {
        def pacienteInstance = Paciente.get(id)
        if (!pacienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pacienteInstance.version > version) {
                pacienteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'paciente.label', default: 'Paciente')] as Object[],
                          "Another user has updated this Paciente while you were editing")
                render(view: "edit", model: [pacienteInstance: pacienteInstance])
                return
            }
        }

        pacienteInstance.properties = params

        if (!pacienteInstance.save(flush: true)) {
            render(view: "edit", model: [pacienteInstance: pacienteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'paciente.label', default: 'Paciente'), pacienteInstance.id])
        redirect(action: "show", id: pacienteInstance.id)
    }

    def delete(Long id) {
        def pacienteInstance = Paciente.get(id)
        if (!pacienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "list")
            return
        }

        try {
            pacienteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'paciente.label', default: 'Paciente'), id])
            redirect(action: "show", id: id)
        }
    }
}
