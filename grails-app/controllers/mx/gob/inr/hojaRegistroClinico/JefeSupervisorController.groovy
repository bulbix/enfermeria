package mx.gob.inr.hojaRegistroClinico

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
}
