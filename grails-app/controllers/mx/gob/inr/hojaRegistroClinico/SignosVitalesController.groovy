package mx.gob.inr.hojaRegistroClinico

class SignosVitalesController {
	
	
	SignosVitalesService signosVitalesService
	def springSecurityService
	
	def guardarEscalaDolor(){
		
		def mensaje = ""
		
		if(signosVitalesService.existeEscalaDolor(params.long('idHoja'), params.int('horaDolor'))){			
			mensaje ="La hora ${params.horaDolor} ya existe en la escala ${params.dolor} del dolor"
		}
		else{			
			signosVitalesService.guardarEscalaDolor(params.dolor,params.long('idHoja'),params.int('horaDolor'),
				springSecurityService.currentUser)
			mensaje = "La hora ${params.horaDolor} anadida a la escala ${params.dolor} del dolor"	
		}
				
		render(contentType: 'text/json') {['mensaje': mensaje]}		
	}
	
	def mostrarEscalaDolor(){
		
		def htmlTabla = signosVitalesService.consultarEscalaDolorHtml(params.long('idHoja'))
		
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
	
	def borrarDetalleDolor(){
		
		signosVitalesService.borrarDetalleDolor(params.long('idRegistro'))
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	
	
}
