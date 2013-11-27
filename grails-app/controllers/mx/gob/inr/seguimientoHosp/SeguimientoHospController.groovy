package mx.gob.inr.seguimientoHosp

class SeguimientoHospController {
	
	def utilService
	def springSecurityService
	def seguimientoHospService
	def estudioService
	def cirugiaService

    def index() {
		
		def seguimientoHosp = new SeguimientoHosp()
		
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser				
		[seguimientoHosp:seguimientoHosp,pisos:pisos,usuarioActual:usuarioActual,
		soloLectura:false, resultTipoAgendas:null, notaOperatoria:null]
		
	}
	
	def medicamento(){
		
	}
	
	def estudio(){
		
	}
	
	def cirugia(){
		
	}
	
	def terapia(){
		
	}
	
	def consultarSeguimiento(){
		
		def idSeguimiento=params.long('idSeguimiento')	
		def mensaje = params.mensaje
		
		def seguimientoHosp = seguimientoHospService.consultarSeguimiento(idSeguimiento)
		def resultTipoAgendas = estudioService.
			consultarTipoAgendas(seguimientoHosp.fechaElaboracion, seguimientoHosp.paciente)
			
		def notasOperatoria = cirugiaService.consultarNotasOperatoria(seguimientoHosp.fechaElaboracion,
			 seguimientoHosp.paciente)
		
		
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser
		def soloLectura = seguimientoHospService.seguimientoSoloLectura(seguimientoHosp?.fechaElaboracion)
						
		def model = [seguimientoHosp:seguimientoHosp,pisos:pisos,mensaje:mensaje,
		usuarioActual:usuarioActual,soloLectura:soloLectura,
		resultTipoAgendas:resultTipoAgendas, notasOperatoria:notasOperatoria]
				
		render(view:'index', model:model);
	}
	
	def consultarSeguimientos(){
		def htmlTabla = seguimientoHospService.consultarSeguimientos(params.long('idPaciente'))
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
	
	def existeFechaElaboracion(){
		def fechaElaboracion = new Date().parse("dd/MM/yyyy",params.fechaElaboracion)
		def result = seguimientoHospService.existeFechaElaboracion(params.long('idPaciente'),fechaElaboracion)
		render(contentType: 'text/json') {['result': result]}
	}
	
}
