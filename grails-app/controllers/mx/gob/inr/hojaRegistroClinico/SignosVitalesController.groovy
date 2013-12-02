package mx.gob.inr.hojaRegistroClinico

class SignosVitalesController {
	
	
	SignosVitalesService signosVitalesService
	def springSecurityService
	
	def guardarEscalaDolor(){
		
		def mensaje = ""
		def status = ""
		
		if(signosVitalesService.existeEscalaDolor(params.long('idHoja'), params.int('horaDolor'))){			
			mensaje ="La hora ${params.horaDolor} ya existe en la escala del dolor"
			status="error"
		}
		else{			
			signosVitalesService.guardarEscalaDolor(params.dolor,params.long('idHoja'),params.int('horaDolor'),
				springSecurityService.currentUser)
			mensaje = "La hora ${params.horaDolor} guardado con la escala ${params.dolor} del dolor"
			status="ok"
		}
				
		render(contentType: 'text/json') {['mensaje': mensaje, status:status]}		
	}
	
	def mostrarEscalaDolor(){
		
		def htmlTabla = signosVitalesService.consultarEscalaDolorHtml(params.long('idHoja'),
			springSecurityService.currentUser)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
	
	def borrarDetalleDolor(){
		
		signosVitalesService.borrarDetalleDolor(params.long('idRegistro'))
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	def borrarAllDetalleDolor(){
		
		signosVitalesService.borrarAllDetalleDolor(params.long('idHoja'),springSecurityService.currentUser)
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	
}
