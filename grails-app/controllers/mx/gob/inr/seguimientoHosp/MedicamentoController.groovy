package mx.gob.inr.seguimientoHosp

import grails.converters.JSON

class MedicamentoController {
	
	
	def medicamentoService

    def listarArticulo(){
		render medicamentoService.listarArticulo(params.term) as JSON
	}	
	
	def buscarArticulo(){
		
		def clave = params.long('id')
		def articulo = medicamentoService.buscarArticulo(clave)
		def articuloJSON = articulo as JSON
		render articuloJSON
		
	}
}
