package mx.gob.inr.hojaRegistroClinico

import java.util.List;

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.IndicadorCalidad;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.seguridad.*

class IndicadoresCalidadService {
	
	
	def controlLiquidosMedicamentosService
	
	def guardarPrevencion(Long idHoja, Integer hora, Integer idProcedimiento, Usuario usuario){
		
		RegistroIngresoEgreso registro = new RegistroIngresoEgreso()
		registro.hoja = HojaRegistroEnfermeria.get(idHoja)
		registro.usuario = usuario
		registro.hora = hora
		registro.rubro = CatRubroNotaEnfermeria.get(R_PREVENSION_CAIDAS)
		def procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
		registro.procedimiento = procedimiento
		registro.totalingresar = procedimiento.descripcion.charAt(1)//Obtenemos el valor entre parentesis
		
		registro.save([validate:false])
		
		
	}
	
	def consultarDetallePrevencionHtml(Long idHoja, Integer idProcedimiento, Usuario usuario){
		controlLiquidosMedicamentosService.consultarDetalleLiquidoHtml(idHoja, "", usuario, R_PREVENSION_CAIDAS,idProcedimiento)
	}
	

	List<RegistroIngresoEgreso> consultarDetallePrevensionCaidas(Long idHoja, String descripcion,Short rubro){
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",R_PREVENSION_CAIDAS as long)
			eq("descripcion",descripcion)
		}

		registros
			
	}
	
	 List<RegistroHojaEnfermeria> consultarEscalaMadox(Long idHoja){
		 
		List<RegistroHojaEnfermeria> escalaMadox = [
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_ESCALA_MADDOX_MATUTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_ESCALA_MADDOX_VESPERTINO)),
			 new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_ESCALA_MADDOX_NOCTURNO))]
		
		escalaMadox = escalaMadox.collect{ escala->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",escala.procedimiento.id)
				maxResults(1)
			}
			
			if(registro){
				escala = registro
			}
			
			escala			
		}
				
		escalaMadox
	}
	 
	 List<RegistroHojaEnfermeria> consultarIndicadores(Long idHoja){
		 
		List<IndicadorCalidad> indicadores = 
		[new IndicadorCalidad(), new IndicadorCalidad()]
		
		
		def procedimientosVenoso = ['fechaInstalacion':P_FECHA_INSTALACION_V,
			'diasConsecutivos':P_DIAS_V,'calibre':P_CALIBRE_V]
		def procedimientosSonda = ['fechaInstalacion':P_FECHA_INSTALACION_S,
			'diasConsecutivos':P_DIAS_S,'material':P_MATERIAL_S,'calibre':P_CALIBRE_S,
			'globo':P_GLOBO_S]	
		
		procedimientosVenoso.each{propiedad,procedimiento->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",procedimiento as long)
				maxResults(1)
			}
			
			if(registro){
				indicadores[0]."${propiedad}" = registro.otro
			}
		}
		
		procedimientosSonda.each{propiedad,procedimiento->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",procedimiento as long)
				maxResults(1)
			}
			
			if(registro){
				indicadores[1]."${propiedad}" = registro.otro
			}
		}
		
		
				
		indicadores
	}
	
	 
	 List<RegistroHojaEnfermeria> consultarPlaneacionObservaciones(Long idHoja){
		 
		List<RegistroHojaEnfermeria> planeacionObservacion = [
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIAGNOSTICO_MATUTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIAGNOSTICO_VESPERTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIAGNOSTICO_NOCTURNO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_OBSERVACIONES_MATUTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_OBSERVACIONES_VESPERTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_OBSERVACIONES_NOCTURNO))]
		
		planeacionObservacion = planeacionObservacion.collect{ planeacion->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",planeacion.procedimiento.id)
				maxResults(1)
			}
			
			if(registro){
				planeacion = registro
			}
			
			planeacion
		}
				
		planeacionObservacion
	}
	
	
	def existeHoraPrevencion(Long idHoja, String descripcion, Integer hora, Long idProcedimiento){
		controlLiquidosMedicamentosService.existeHoraLiquido(idHoja, descripcion, R_PREVENSION_CAIDAS, hora, idProcedimiento)
	}
	
	def borrarAllDetallePrevencion(Long idHoja, String descripcion, Usuario usuario, Long idProcedimiento){
		controlLiquidosMedicamentosService.borrarAllDetalleLiquido(idHoja,descripcion,usuario,R_PREVENSION_CAIDAS,idProcedimiento)
	}
	
	
}
