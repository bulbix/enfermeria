package mx.gob.inr.hojaRegistroClinico

class SignosVitalesController {
	
	
	SignosVitalesService signosVitalesService
	

   def guardarSignosVitales(){
		def horas = params.list('hora')
		def temperaturaList = params.list('temperatura')
		def cardiacaList = params.list('cardiaca')
		def sistolicaList = params.list('sistolica')
		def diastolicaList = params.list('diastolica')
		def respiracionList = params.list('respiracion')
		def gabineteList = params.list('gabinete')
		
		signosVitalesService.guardarSignosVitales(params.long('idHoja'),6558, horas, 
			temperaturaList, cardiacaList, sistolicaList, diastolicaList, respiracionList, gabineteList)
		
		
		
		def dietaList = params.list('dieta')
		def horaDietaList =[]
		//Setea la hora de la dieta genralizada en nulo
		horaDietaList << "-1"		
		horaDietaList.addAll(params.list('horaDieta'))		
		
		signosVitalesService.guardarDietas(params.long('idHoja'),6558, dietaList, horaDietaList)				
		
		render "Signos Vitales salvado correctamente "
	}	
	
	def guardarEscalaDolor(){
		
		def mensaje = ""
		
		if(signosVitalesService.existeEscalaDolor(params.long('idHoja'), params.int('horaDolor'))){			
			mensaje ="La hora ${params.horaDolor} ya existe en la escala ${params.dolor} del dolor"
		}
		else{			
			signosVitalesService.guardarEscalaDolor(params.dolor,params.long('idHoja'),params.int('horaDolor'),6558)
			mensaje = "La hora ${params.horaDolor} anadida a la escala ${params.dolor} del dolor"	
		}
				
		render(contentType: 'text/json') {['mensaje': mensaje]}		
	}
	
	def mostrarEscalaDolor(){
		
		def htmlTabla = signosVitalesService.consultarEscalaDolorHtml(params.long('idHoja'))
		
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
	
	def borrarDetalleDolor(){
		
		signosVitalesService.borrarDetalleDolor(params.long('idRegistro'))
		
		render(contentType: 'text/json') {['borrado': 'true}']}
		
	}
	
	
	
}
