package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.catalogos.*;
import mx.gob.inr.utils.*;
import org.grails.plugins.wsclient.service.WebService

class HojaRegistroClinicoController {	
	
	
	HojaRegistroClinicoService hojaRegistroClinicoService
	UtilService utilService
	
	/***
	 * Pantalla Principal de la hoja
	 * @return
	 */
	def index(){		
		def hojaInstance = new HojaRegistroEnfermeria()	
		def pisos = utilService.consultarPisos()		
		[hojaInstance:hojaInstance,pisos:pisos]
	}
		
	def consultarHoja(Long id){
		def hojaInstance = hojaRegistroClinicoService.consultarHoja(id)			
		def pisos = utilService.consultarPisos()		
		def model = [hojaInstance:hojaInstance,pisos:pisos]		
		render(view:'index', model:model);		
	}
	
	///######## INICIA SUBVISTAS#################
	def alergiasComorbilidad(){		
	}
	
	def signosVitales(){
	}
	
	def controlLiquidosMedicamentos(){
	}
	
	def valoracionEnfermeria(){
	}
	
	def diagnosticosIntervenciones(){		
	}
	
	def indicadoresCalidad(){		
	}	
	///########FIN SUBVISTAS#################
	
	def guardarHojaTurno(){
		def hoja = hojaRegistroClinicoService.guardarHojaTurno(params,6558)
		redirect(action:"consultarHoja", id:hoja.id )		
	}
	
	def guardarSignosVitales(){
		def horas = params.list('hora')
		def temperaturaList = params.list('temperatura')
		def cardiacaList = params.list('cardiaca')
		def sistolicaList = params.list('sistolica')
		def diastolicaList = params.list('diastolica')
		def respiracionList = params.list('respiracion')
		def gabineteList = params.list('gabinete')
		
		hojaRegistroClinicoService.guardarSignosVitales(params.long('idHoja'),6558, horas, 
			temperaturaList, cardiacaList, sistolicaList, diastolicaList, respiracionList, gabineteList)
		
		
		
		def dietaList = params.list('dieta')
		def horaDietaList =[]
		//Setea la hora de la dieta genralizada en nulo
		horaDietaList << "-1"		
		horaDietaList.addAll(params.list('horaDieta'))		
		
		hojaRegistroClinicoService.guardarDietas(params.long('idHoja'),6558, dietaList, horaDietaList)				
		
		redirect(action:"consultarHoja", id:params.idHoja )
	}	
	
	def guardarEscalaDolor(){		
		
		
		hojaRegistroClinicoService.guardarEscalaDolor(params.dolor,params.long('idHoja'),params.int('horaDolor'),6558)
		
		redirect(action:"consultarHoja", id:params.idHoja )
		
	}
		
}
