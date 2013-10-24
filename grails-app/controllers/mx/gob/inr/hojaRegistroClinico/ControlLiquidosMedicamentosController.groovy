package mx.gob.inr.hojaRegistroClinico

import grails.converters.JSON

class ControlLiquidosMedicamentosController {

	ControlLiquidosMedicamentosService controlLiquidosMedicamentosService
	
    def guardarIngreso(){		
		controlLiquidosMedicamentosService.guardarIngreso(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Ingreso salvado correctamente']}
	}
	
	def guardarEgreso(){
		controlLiquidosMedicamentosService.guardarEgreso(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Egreso salvado correctamente']}
	}
	
	def guardarMedicamento(){
		controlLiquidosMedicamentosService.guardarMedicamento(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Medicamento salvado correctamente']}
	}
	
	def guardarEscalaOtro(){
		controlLiquidosMedicamentosService.guardarEscalaOtro(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'EscalaOtro salvado correctamente']}
	}
	
	
	
	def guardarFaltante(){
		controlLiquidosMedicamentosService.guardarFaltante(params,6558)
		
		render(contentType: 'text/json') {['mensaje': 'Faltante salvado correctamente']}
	}	
	
	def consultarDetalleIngreso(){
		
		def htmlTabla = controlLiquidosMedicamentosService.
		consultarDetalleIngresoHtml(params.long('idHoja'),params.descripcion,params.int('numeroRenglon'),6558)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
		
	}
	
	def consultarDetalleEgreso(){
		
		def htmlTabla = controlLiquidosMedicamentosService.
		consultarDetalleEgreso(params.long('idHoja'),params.descripcion,params.int('numeroRenglon'),6558)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
		
	}
	
	def consultarDetalleMedicamento(){
		
		def htmlTabla = controlLiquidosMedicamentosService.
		consultarDetalleMedicamento(params.long('idHoja'),params.descripcion,params.int('numeroRenglon'),6558)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
		
	}
	
	def consultarDetalleEscalaOtro(){
		
		def htmlTabla = controlLiquidosMedicamentosService.
		consultarDetalleEscalaOtro(params.long('idHoja'),params.descripcion,params.int('numeroRenglon'),6558)
		
		render(contentType: 'text/json') {['html': htmlTabla]}
		
	}
	
	
	
	
	
	def borrarDetalleLiquido(){
		
		controlLiquidosMedicamentosService.borrarDetalleLiquido(params.long('idRegistro'))
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	def borrarAllDetalleIngreso(){
		
		controlLiquidosMedicamentosService.borrarAllDetalleIngreso(params.long('idHoja'),params.descripcion)
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	def listarIngresos(){
		render controlLiquidosMedicamentosService.listarIngresos(params.term) as JSON
	}
	
	def listarMedicamentos(){
		render controlLiquidosMedicamentosService.listarMedicamentos(params.term) as JSON
	}
	
	def listarEscalaOtros(){
		render controlLiquidosMedicamentosService.listarEscalaOtros(params.term) as JSON
	}
	
}
