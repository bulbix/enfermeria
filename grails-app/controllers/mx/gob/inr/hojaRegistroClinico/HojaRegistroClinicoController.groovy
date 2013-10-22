package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.catalogos.*;
import mx.gob.inr.utils.*;
import org.grails.plugins.wsclient.service.WebService
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class HojaRegistroClinicoController {	
	
	
	HojaRegistroClinicoService hojaRegistroClinicoService
	UtilService utilService
	
	/***
	 * Pantalla Principal de la hoja
	 * @return
	 */
	def index(){		
		def hojaInstance = new HojaRegistroEnfermeria()
		hojaInstance.rubrosValoracion = utilService.consultarCatalogoRubro(S_VALORACION)
		hojaInstance.rubrosDiagnostico = utilService.consultarCatalogoRubro(S_DIAGNOSTICOS_INTERVENCIONES)
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
		//render(contentType: 'text/json') {['idHoja':hoja.id,'mensaje': 'Escala dolor añadido correctamente']}
	}
		
}
