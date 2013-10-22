package mx.gob.inr.utils

class UtilController {
	
	UtilService utilService

	def guardarCheckTabla(){
		utilService.guardarCheckTabla(params.long('idHoja'), params.long('idProcedimiento'),
		6558, Turno."$params.turno", params.boolean('valor'))
		
		render(contentType: 'text/json') {['mensaje': 'Check salvado correctamente']}
	}
		
	def guardarTextTabla(){
		utilService.guardarRegistroEnfermeriaConValor(null,params.long('idHoja'), params.long('idProcedimiento'), 6558, params.valor,true)
		
		render(contentType: 'text/json') {['mensaje': 'Texto salvado correctamente']}
	}
	
	def guardarTextTablaSinBorrar(){
		utilService.guardarRegistroEnfermeria(null,params.long('idHoja'), params.long('idProcedimiento'), 6558, params.valor,true)
		
		render(contentType: 'text/json') {['mensaje': 'Texto salvado correctamente']}
	}
}
