package mx.gob.inr.hojaRegistroClinico

import java.util.List;

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.IndicadorCalidad;
import mx.gob.inr.utils.Turno
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
	
	List<RegistroIngresoEgreso> consultarDetallePrevencionCaidas(Long idHoja, String descripcion = null){
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",R_PREVENSION_CAIDAS as long)
			if(descripcion){
				eq("descripcion",descripcion)
			}
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
		[new IndicadorCalidad(), new IndicadorCalidad(), new IndicadorCalidad()]
		
		
		def procedimientosVenoso = ['fechaInstalacion':[P_FECHA_INSTALACION_V, P_FECHA_INSTALACION_V2],
			'diasConsecutivos':[P_DIAS_V,P_DIAS_V2],'calibre':[P_CALIBRE_V,P_CALIBRE_V2]]
		def procedimientosSonda = ['fechaInstalacion':P_FECHA_INSTALACION_S,'diasConsecutivos':P_DIAS_S,'material':P_MATERIAL_S,
			'calibre':P_CALIBRE_S,'globo':P_GLOBO_S]	
		
		procedimientosVenoso.each{propiedad,procedimientos->
			
			procedimientos.eachWithIndex { procedimiento, index ->
				
				def registro = RegistroHojaEnfermeria.createCriteria().get{
					eq("hoja.id", idHoja)
					eq("procedimiento.id",procedimiento as long)
					maxResults(1)
				}
				
				if(registro){
					indicadores[index]."${propiedad}" = registro.otro
				}				
			}	
			
		}
		
		procedimientosSonda.each{propiedad,procedimiento->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",procedimiento as long)
				maxResults(1)
			}
			
			if(registro){
				indicadores[2]."${propiedad}" = registro.otro
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
		
	List<List<RegistroIngresoEgreso>> consultarPrimeraValoracion(Long idHoja){		
		
		int[][] primeraValoracion = new int[3][6];
		
		def auxiliar = [:]		
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",R_PREVENSION_CAIDAS as long)		
						
		}.each{ registro ->
		
			def elemento = auxiliar.find { it.key == registro.hora }	
			
			if(elemento){
				elemento.value << registro
			}
			else{
				auxiliar[registro.hora]  = [registro]
			}
		}		
		
		for (horaM in 8..14) {
			primeraValoracion[0] =  auxPrimeraValoracion(auxiliar[horaM])

			if(primeraValoracion[0][5]!=0){//Sumatoria
				break
			}
		}

		for (horaV in 15..20) {
			primeraValoracion[1] =  auxPrimeraValoracion(auxiliar[horaV])

			if(primeraValoracion[1][5]!=0){//Sumatoria
				break
			}
		}

		for (horaN in 21..24) {
			primeraValoracion[2] =  auxPrimeraValoracion(auxiliar[horaN])

			if(primeraValoracion[2][5]!=0){//Sumatoria
				break
			}
		}

		if (primeraValoracion[2][5] == 0) {
			for (horaN in 1..7) {
				primeraValoracion[2] =  auxPrimeraValoracion(auxiliar[horaN])

				if(primeraValoracion[2][5]!=0){//Sumatoria
					break
				}
			}
		}
			

		primeraValoracion
	}
	
	int[] auxPrimeraValoracion(lista){
		int[] fila =  new int[6]
		
		def procedimientos = CatProcedimientoNotaEnfermeria.createCriteria().list{
			eq("padre.id", R_PREVENSION_CAIDAS as long )
			order("id")
		}.eachWithIndex{procedimiento, index->
			
			def registro = lista.find{r -> r.procedimiento == procedimiento}
		
			if(registro){
				fila[index] = registro.totalingresar as int
			}	
		}
		
		for(index in 0..4){
			fila[5] += fila[index]
		}
		
		fila
	}
	
	
	
	
}
