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
		
		//println idPadre
		
		if(!idPadre){//Centinela
			seguimientoHosp = medicamentoService.guardarSeguimientoHosp(jsonPadre, springSecurityService.currentUser)
			
			for(detalle in jsonDetalle){
				medicamentoService.guardarMedicamento(detalle, seguimientoHosp)
			}			
			
		}
		else{
			seguimientoHosp = SeguimientoHosp.read(idPadre)
			medicamentoService.guardarMedicamento(jsonDetalle[0], seguimientoHosp)
		}
		render(contentType: 'text/json') {['idSeguimiento': seguimientoHosp.id]}
		
	}
	
	def consultarDetalle(){
		def json = medicamentoService.consultarDetalleMedicamento(params.long('idSeguimiento')) as JSON
		render json	
	}	
	
	def actualizarDetalle(params){	
		
		def id = params.long('id')
		def mensaje = "success"
		
		switch(params.oper){
			case "edit":
				def medicamento = SeguimientoHospMedicamento.get(id)
				medicamento.cantidad = params.cantidad as int
				medicamento.save([validate:false])				
				break
			case "del":
				SeguimientoHospMedicamento.get(id).delete()				
				break
		}
		
		render(contentType: 'text/json') {['mensaje': mensaje]}
		
	}
	
	
	def medicamentosHistorico(){
		
		def idPaciente = params.idPaciente as long
		def fechaElaboracion = new Date().parse("dd/MM/yyyy",params.fechaElaboracion)
		
		def jsonArray = medicamentoService.medicamentosHistorico(idPaciente, fechaElaboracion) as JSON

		render jsonArray
		
		
	}
}
