package mx.gob.inr.hojaRegistroClinico

class IndicadoresCalidadController {

	
	def indicadoresCalidadService
	def springSecurityService
	
    def guardarIndicadores() {
		
		render "Indicadores de calidad salvado correctamente"		
	}
	
	def guardarPrevencion(){
		
		indicadoresCalidadService.guardarPrevencion(params.long('idHoja'),params.int('hora'),
			params.int('idProcedimiento'),springSecurityService.currentUser)
		
		render(contentType: 'text/json') {['mensaje': 'Prevencion salvado correctamente']}
		
	}
	
	def consultarDetallePrevencion(){
		
		def htmlTabla = indicadoresCalidadService.
		consultarDetallePrevencionHtml(params.long('idHoja'),params.int('idProcedimiento'),springSecurityService.currentUser)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
}
