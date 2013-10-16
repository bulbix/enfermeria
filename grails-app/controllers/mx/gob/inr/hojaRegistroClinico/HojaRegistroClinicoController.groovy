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
	
	
	def consultar(Long id){
				
		def hojaInstance = hojaRegistroClinicoService.consultar(id)			
		def pisos = utilService.consultarPisos()		
		def model = [hojaInstance:hojaInstance,pisos:pisos]		
		render(view:'index', model:model);
		
		
	}
	
	///########SUBVISTAS#################
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
	
	///########SUBVISTAS#################
	
	def guardar(){
		def hoja = hojaRegistroClinicoService.guardarAlergiasComorbilidad(params)
		redirect(action:"consultar", id:hoja.id )		
	}	
	
}
