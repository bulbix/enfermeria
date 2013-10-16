package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.utils.*;
import mx.gob.inr.seguridad.*;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class HojaRegistroClinicoService {

	static transactional = true

	/***
	 * 
	 * @param params
	 * @return la hoja
	 */
	def guardarAlergiasComorbilidad(params){

		def hoja = new HojaRegistroEnfermeria()

		if(params.idHoja)
			hoja = HojaRegistroEnfermeria.get(params.idHoja)

		hoja.properties = params
		hoja.has = params.has == 'on'
		hoja.dm = params.dm == 'on'
		hoja.nef = params.nef == 'on'
		hoja.ic = params.ic == 'on'
		hoja.ir = params.ir == 'on'

		hoja.asignarComorbilidad()

		hoja.admision = AdmisionHospitalaria.get(params.idAdmision)
		hoja.paciente  = Paciente.get(params.idPaciente)
		hoja.save([validate:false])
		
		hoja

	}


	def consultar(Long idHoja){

		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idHoja)
		}
		
		hoja.signosVitales = consultarSignosVitales(idHoja)
		hoja.escalaDolor = consultarEscalaDolor(idHoja)

		return hoja
	}
	
	def consultarSignosVitales(Long idHoja){
		
		def result = []
		
		def procedimientosSignos = ['temperatura':P_TEMEPRATURA,
			'cardiaca':P_FRECUENCIA_CARDIACA,
			'diastolica':P_TENSION_ARTERIAL_DIASTOLICA,
			'sistolica':P_TENSION_ARTERIAL_SISTOLICA,
			'respiracion':P_FRECUENCIA_RESPIRATORIA,
			'gabinete':P_LABORATORIO_GABINETE]
		
		procedimientosSignos.each{ metodo, procedimientoSigno ->
			
			def registros = RegistroHojaEnfermeria.createCriteria().list(){
				eq("hoja.id",idHoja)
				eq("procedimiento.id",procedimientoSigno as long)
			}.each{
			
				def signoVital = result.find { s -> s.hora == it.horaregistrodiagva }
				
				if(signoVital){
					signoVital."${metodo}" = it
				}
				else{
					signoVital = new SignoVital()
					signoVital.hora = it.horaregistrodiagva
					signoVital."${metodo}" = it
					result << signoVital
				}
			}			
		}	
		
		//un valor centinela para agregar mas registros
		if(!result){
			result << new SignoVital(hora:1)
		}
		
		result
		
	}

	def consultarEscalaDolor(Long idHoja){
		def registros = RegistroHojaEnfermeria.createCriteria().list(){
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_ESCALA_DOLOR as long)
			}
		}
		
		registros
		
	}
	
	def guardarRegistroEnfermeriaConValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario,String valor){
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("procedimiento.id",idProcedimiento as long)
			eq("horaregistrodiagva",hora)
			eq("hoja.id",idHoja)
		}
		
		if(registro){
			if(valor){
				registro.otro = valor
				registro.save([validate:false])
			}
			else{
				registro.delete()
			}			
		}
		else{
			if(valor){
				registro = new RegistroHojaEnfermeria()
				registro.horaregistrodiagva = hora
				registro.hoja = HojaRegistroEnfermeria.get(idHoja)
				registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
				registro.usuario =Usuario.get(6558)
				registro.otro = valor
				registro.save([validate:false])
			}
		}		
	}
	
	def guardarRegistroEnfermeriaSinValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario){
				
		def registro = new RegistroHojaEnfermeria()
		registro.horaregistrodiagva = hora
		registro.hoja = HojaRegistroEnfermeria.get(idHoja)
		registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
		registro.usuario =Usuario.get(6558)
		registro.otro = ""
		registro.save([validate:false])
	}




}
