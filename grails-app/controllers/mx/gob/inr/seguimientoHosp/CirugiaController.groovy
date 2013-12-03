package mx.gob.inr.seguimientoHosp

class CirugiaController {
	
	def cirugiaService

    def guardarCostoCirugia(){
		cirugiaService.guardarCostoCirugia(params.long('idSeguimiento'), params.long('idNota'),
		params.long('idDiagnostico'),params.float('costo'))
		
		def importeTotal = cirugiaService.importeTotalCirugia(params.long('idSeguimiento'))
		
		render(contentType: 'text/json') {['importeTotal': importeTotal]}
	}
}
