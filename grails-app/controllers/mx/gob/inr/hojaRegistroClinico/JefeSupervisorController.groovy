package mx.gob.inr.hojaRegistroClinico

import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ENFERMERIA_JEFE_SUPERVISOR'])
class JefeSupervisorController {
	
	def jefeSupervisorService
	def springSecurityService

    def consultarHojas(){
		def htmlTabla = jefeSupervisorService.consultarHojas(params.long('idPaciente'),params.turno)
		render(contentType: 'text/json') {['html': htmlTabla]}
	}	
	
	def mostrarFirma(){		
		def htmlTabla = jefeSupervisorService.mostrarFirma(params.long('idHoja'), 
			params.tipoUsuario, params.turnoAsociar,params.fechaElaboracion)
		render(contentType: 'text/json') {['html': htmlTabla]}		
	}
	
	def firmarHoja(){
		
		def password = params.passwordFirma
		Long idHoja = params.long('idHoja')
		def turnoAsociar = params.turnoAsociar
		def tipoUsuario = params.tipoUsuario
		
		def firmado =	jefeSupervisorService.
		firmarHoja(idHoja,turnoAsociar, springSecurityService.currentUser, password, tipoUsuario)
		
		render(contentType: 'text/json') {['firmado':firmado]}		
	}
	
	
	def eliminarTurnoUsuarioHoja(){
		def mensaje = jefeSupervisorService.eliminarTurnoUsuarioHoja(params.long('idHoja'), params.turno, params.long('idUsuario'), params.tipoUsuario)
		render(contentType: 'text/json') {['mensaje': mensaje]}
	}
}
