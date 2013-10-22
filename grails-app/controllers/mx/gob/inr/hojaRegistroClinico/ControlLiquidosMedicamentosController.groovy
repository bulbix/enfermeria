package mx.gob.inr.hojaRegistroClinico

class ControlLiquidosMedicamentosController {

	ControlLiquidosMedicamentosService controlLiquidosMedicamentosService
	
    def guardarIngreso(){		
		controlLiquidosMedicamentosService.guardarIngreso(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Ingreso salvado correctamente']}
	}
	
	def guardarFaltante(){
		controlLiquidosMedicamentosService.guardarFaltante(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Faltante salvado correctamente']}
	}	
	
	def consultarDetalleIngreso(){
		
		def htmlTabla = controlLiquidosMedicamentosService.
		consultarDetalleIngreso(params.long('idHoja'),params.descripcion,params.int('numeroRenglon'),6558)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
		
	}
	
	def borrarDetalleIngreso(){
		
		controlLiquidosMedicamentosService.borrarDetalleIngreso(params.long('idRegistro'))
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	def borrarAllDetalleIngreso(){
		
		controlLiquidosMedicamentosService.borrarAllDetalleIngreso(params.long('idHoja'),params.descripcion)
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
}
