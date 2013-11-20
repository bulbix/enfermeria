package mx.gob.inr.seguimientoHosp

import grails.converters.JSON

class MedicamentoController {
	
	
	def medicamentoService
	def springSecurityService

    def listarArticulo(){
		render medicamentoService.listarArticulo(params.term) as JSON
	}	
	
	def buscarArticulo(){
		
		def clave = params.long('id')
		def articulo = medicamentoService.buscarArticulo(clave)
		
		render articulo?.properties as JSON
				
	}
	
	
	def guardar(){
		
		SeguimientoHosp seguimientoHosp
		def jsonPadre = JSON.parse(params.dataPadre)
		def jsonDetalle = JSON.parse(params.dataDetalle)
		def idPadre = params.int('idPadre')
		
		if(!idPadre){//Centinela
			seguimientoHosp = medicamentoService.guardarSeguimientoHosp(springSecurityService.currentUser)			
			medicamentoService.guardarMedicamento(jsonDetalle[0], entity, null,session.almacen)
		}
		else{
			seguimientoHosp = SeguimientoHosp.read(idPadre)
			medicamentoService.guardarMedicamento(jsonDetalle[0],entity,null,session.almacen)
		}
		render(contentType: 'text/json') {['idPadre': entity.id]}
		
	}
}
