package mx.gob.inr.hojaRegistroClinico

import grails.converters.JSON

class ControlLiquidosMedicamentosController {

	def controlLiquidosMedicamentosService
	def springSecurityService	

	def guardarLiquido(){				
		controlLiquidosMedicamentosService."guardar${params.tipo}"(params,springSecurityService.currentUser)		
		render(contentType: 'text/json') {['mensaje': 'EscalaOtro salvado correctamente']}		
	}
	
	def guardarFaltante(){
		controlLiquidosMedicamentosService.guardarFaltante(params,springSecurityService.currentUser)		
		render(contentType: 'text/json') {['mensaje': 'Faltante salvado correctamente']}
	}
	
	def consultarDetalleLiquidoHtml(){		
		def htmlTabla = controlLiquidosMedicamentosService.
		"consultarDetalle${params.tipo}Html"(params.long('idHoja'),params.descripcion,springSecurityService.currentUser)		
		render(contentType: 'text/json') {['html': htmlTabla]}		
	}	
	
	def borrarDetalleLiquido(){		
		controlLiquidosMedicamentosService.borrarDetalleLiquido(params.long('idRegistro'))		
		render(contentType: 'text/json') {['borrado': 'true}']}	
	}
	
	def borrarAllDetalleLiquido(){
		
		controlLiquidosMedicamentosService."borrarAllDetalle${params.tipo}"(params.long('idHoja'),params.descripcion,
			springSecurityService.currentUser)		
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
		
	def existeHoraLiquido(){
		
		def existeHora = controlLiquidosMedicamentosService."existeHora${params.tipo}"(params.long('idHoja'), params.descripcion, params.int('hora'))
		
		render(contentType: 'text/json') {['existeHora': existeHora]}
	}
	
	def cambiarLiquido(){
		
		def existeHora = controlLiquidosMedicamentosService."cambiar${params.tipo}"(params.long('idHoja'), params.descripcionOld,params.descripcionNew)
		
		render(contentType: 'text/json') {['status': true]}
	}
	
	
	
}
