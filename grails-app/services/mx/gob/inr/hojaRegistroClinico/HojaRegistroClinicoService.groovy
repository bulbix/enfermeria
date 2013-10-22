package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.*;
import mx.gob.inr.seguridad.*;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import grails.converters.*


class HojaRegistroClinicoService {

	static transactional = true
	
	def signosVitalesService 
	def valoracionEnfermeriaService
	def utilService
	def controlLiquidosMedicamentosService
	
	/***
	 * 
	 * @param params
	 * @return la hoja
	 */
	def guardarHojaTurno(params, Integer idUsuario){

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
		
		def hojaTurno = new HojaRegistroEnfermeriaTurno(
			hoja:hoja,
			usuario:Usuario.get(idUsuario),
			turno:Turno."${params.turno}"			
		)	
		
		
		hoja.save([validate:false])
		hojaTurno.save([validate:false])
		
		hoja

	}

	/***
	 * Consulta de manera completa hoja de enfermeria
	 * @param idHoja
	 * @return hoja
	 */
	def consultarHoja(Long idHoja){

		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idHoja)
		}
		
		hoja.signosVitales = signosVitalesService.consultarSignosVitales(idHoja)		
		hoja.dietas = signosVitalesService.consultarDietas(idHoja)
		hoja.requisitos = valoracionEnfermeriaService.consultarRequisitos(idHoja)
		
		hoja.rubrosValoracion = utilService.consultarCatalogoRubro(S_VALORACION)
		hoja.rubrosDiagnostico = utilService.consultarCatalogoRubro(S_DIAGNOSTICOS_INTERVENCIONES)
		
		hoja.ingresos = controlLiquidosMedicamentosService.consultarIngresos(idHoja)

		return hoja
	}
	
	
	
}
