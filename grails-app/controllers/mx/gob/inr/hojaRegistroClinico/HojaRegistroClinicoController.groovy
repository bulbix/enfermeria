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
	
	def guardarSignosVitales(){
		def horas = params.list('hora')
		def temperaturaList = params.list('temperatura')
		def cardiacaList = params.list('cardiaca')
		def sistolicaList = params.list('sistolica')
		def diastolicaList = params.list('diastolica')
		def respiracionList = params.list('respiracion')
		def gabineteList = params.list('gabinete')
		
		horas.eachWithIndex { hora, index ->
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
			params.long('idHoja'),ConstantesHojaEnfermeria.P_TEMEPRATURA,6558,temperaturaList[index])
			
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
				params.long('idHoja'),ConstantesHojaEnfermeria.P_FRECUENCIA_CARDIACA,6558,cardiacaList[index])
			
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
				params.long('idHoja'),ConstantesHojaEnfermeria.P_TENSION_ARTERIAL_SISTOLICA,6558,sistolicaList[index])
			
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
				params.long('idHoja'),ConstantesHojaEnfermeria.P_TENSION_ARTERIAL_DIASTOLICA,6558,diastolicaList[index])
			
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
				params.long('idHoja'),ConstantesHojaEnfermeria.P_FRECUENCIA_RESPIRATORIA,6558,respiracionList[index])
			
			hojaRegistroClinicoService.guardarRegistroEnfermeriaConValor(hora as int,
				params.long('idHoja'),ConstantesHojaEnfermeria.P_LABORATORIO_GABINETE,6558,gabineteList[index])
			
		}
		
		redirect(action:"consultar", id:params.idHoja )
	}	

	
	def guardarEscalaDolor(){
		
		//render params.dolor
		
		def procedimiento = CatProcedimientoNotaEnfermeria.createCriteria().get{
			eq("padre.id",ConstantesHojaEnfermeria.R_ESCALA_DOLOR as long)
			eq("descripcion",params.dolor)			
		}
		
		hojaRegistroClinicoService.guardarRegistroEnfermeriaSinValor(1,
			params.long('idHoja'),procedimiento.id,6558)
		
		redirect(action:"consultar", id:params.idHoja )
		
	}
		
}
