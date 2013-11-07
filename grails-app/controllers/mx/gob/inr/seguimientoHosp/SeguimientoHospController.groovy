package mx.gob.inr.seguimientoHosp

class SeguimientoHospController {
	
	def utilService
	def springSecurityService

    def index() {
		
		def seguimientoHosp = new SeguimientoHosp()
		
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser				
		[seguimientoHosp:seguimientoHosp,pisos:pisos,usuarioActual:usuarioActual]
		
	}
	
	def medicamento(){
		
	}
	
	def estudio(){
		
	}
	
	def cirugia(){
		
	}
	
	def terapia(){
		
	}
	
}
