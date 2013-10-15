package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.catalogos.*;
import mx.gob.inr.utils.*;
import org.grails.plugins.wsclient.service.WebService

class HojaRegistroClinicoController {	
	
	
	UtilService utilService
	
	/***
	 * Pantalla Principal de la hoja
	 * @return
	 */
	def index(){
		
		def hojaInstance = new HojaRegistroEnfermeria()
		hojaInstance.fechaElaboracion = new Date()		
		
		def pisos = utilService.consultarPisos()
		
		[hojaInstance:hojaInstance,pisos:pisos]
		
	}
	
	/***
	 * Subvista
	 * @return
	 */
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
	
	
	
}
