package mx.gob.inr.hojaRegistroClinico

class IndicadoresCalidadController {

	
	def indicadoresCalidadService
	
    def guardarIndicadores() {
		
		render "Indicadores de calidad salvado correctamente"		
	}
	
	def guardarPrevencion(){
		
		indicadoresCalidadService.guardarPrevencion(params.long('idHoja'),params.int('hora'),
			params.int('idProcedimiento'),6558)
		
		render(contentType: 'text/json') {['mensaje': 'Prevencion salvado correctamente']}
		
	}
	
	def consultarDetallePrevencion(){
		
		def htmlTabla = indicadoresCalidadService.
		consultarDetallePrevencionHtml(params.long('idHoja'),params.int('idProcedimiento'))
		
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
}
