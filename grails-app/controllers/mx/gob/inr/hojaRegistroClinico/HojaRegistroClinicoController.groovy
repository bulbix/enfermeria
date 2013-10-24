package mx.gob.inr.hojaRegistroClinico

import javax.servlet.ServletOutputStream

import mx.gob.inr.catalogos.*;
import mx.gob.inr.reportes.ReporteRegistrosClinicos;
import mx.gob.inr.reportes.Util;
import mx.gob.inr.utils.*;

import org.grails.plugins.wsclient.service.WebService

import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class HojaRegistroClinicoController {	
	
	def reporteHojaFacadeService
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
		hojaInstance.rubrosIndicador = utilService.consultarCatalogoRubro(S_INDICADORES_CALIDAD)
		def pisos = utilService.consultarPisos()		
		[hojaInstance:hojaInstance,pisos:pisos]
	}
		
	def consultarHoja(){
		
		def idHoja=params.long('idHoja')
		def turnoActual=params.turnoActual
		
		def hojaInstance = hojaRegistroClinicoService.consultarHoja(idHoja,turnoActual)			
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
	
	def consultarHojas(){		
		def htmlTabla = hojaRegistroClinicoService.consultarHojas(params.long('idPaciente'))		
		render(contentType: 'text/json') {['html': htmlTabla]}		
	}
	
	
	def mostrarFirma(){		
		def turno = params.turno		
		
		if(hojaRegistroClinicoService.existeTurno(params.long('idHoja'), turno)){			
			render(contentType: 'text/json') {['status': 'cargarHoja']}
		}
		else{
			def htmlTabla = hojaRegistroClinicoService.mostrarFirma(params.long('idHoja'))
			render(contentType: 'text/json') {['html': htmlTabla,'status':'firmarHoja']}
		}			
	}
	
	def firmarHoja(){
		def password = params.passwordFirma
		def idHoja = params.long('idHoja')
		
		def firmado =	hojaRegistroClinicoService.
		firmarHoja(idHoja,params.turno, 6558, password)
		
		render(contentType: 'text/json') {['firmado': firmado]}
	}
	
	
	def reporteHoja(){
		
		def idHoja=params.long('idHoja')
		def turnoActual=params.turnoActual		
		
		def reporte = new ReporteRegistrosClinicos(reporteHojaFacadeService)
		def hojaInstance = hojaRegistroClinicoService.consultarHoja(786,"MATUTINO")
		byte[] bytes = reporte.generarReporte(hojaInstance)
		def datos =  new ByteArrayInputStream(bytes)
		
		String cadenaRandom = Util.getCadenaAlfanumAleatoria(8);
				
		 def FileNameReport = "ReporteHoja" + cadenaRandom +".pdf";
		 
		 Util.mostrarReporte(response,datos,'application/pdf',FileNameReport)		
		
	}
	
		
}
