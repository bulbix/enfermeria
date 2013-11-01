package mx.gob.inr.utils

class UtilController {
	
	UtilService utilService
	def springSecurityService

	def guardarCheckTabla(){
		utilService.guardarCheckTabla(params.long('idHoja'), params.long('idProcedimiento'),
		springSecurityService.currentUser, Turno."$params.turno", params.boolean('valor'))
		
		render(contentType: 'text/json') {['mensaje': 'Check salvado correctamente']}
	}	
	
	def guardarRadioTabla(){
		utilService.guardarRadioTabla(params.long('idHoja'), params.long('idProcedimiento'),
		springSecurityService.currentUser, params.valor)
		
		render(contentType: 'text/json') {['mensaje': 'Radio salvado correctamente']}
	}
	
	def borrarRadioTabla(){
		utilService.borrarRadioTabla(params.long('idHoja'), params.long('idProcedimiento'))
		
		render(contentType: 'text/json') {['mensaje': 'Radio borrado correctamente']}
	}
		
	def guardarTextTabla(){
		utilService.guardarRegistroEnfermeriaConValor(null,params.long('idHoja'), params.long('idProcedimiento'),
		springSecurityService.currentUser, params.valor,true)
		
		render(contentType: 'text/json') {['mensaje': 'Texto salvado correctamente']}
	}
	
	def guardarTextTablaConHora(){
		utilService.guardarRegistroEnfermeriaConValor(params.int('hora'),params.long('idHoja'),
			 params.long('idProcedimiento'), springSecurityService.currentUser,
			  params.valor,params.boolean('modificarHora'))
		
		render(contentType: 'text/json') {['mensaje': 'Texto con hora salvado correctamente']}
	}
	
	def guardarTextTablaSinBorrar(){
		utilService.guardarRegistroEnfermeria(null,params.long('idHoja'), params.long('idProcedimiento'),
		springSecurityService.currentUser, params.valor,true)
		
		render(contentType: 'text/json') {['mensaje': 'Texto salvado correctamente']}
	}
}
