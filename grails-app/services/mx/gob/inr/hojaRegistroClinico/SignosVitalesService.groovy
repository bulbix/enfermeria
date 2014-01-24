	package mx.gob.inr.hojaRegistroClinico

import java.util.List;

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.SignoVital
import mx.gob.inr.utils.UtilService
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.catalogos.*

class SignosVitalesService {
	
	UtilService utilService
	
	def consultarSignosVitales(Long idHoja){
		
		def result = []
		
		def procedimientosSignos = ['temperatura':P_TEMEPRATURA,
			'cardiaca':P_FRECUENCIA_CARDIACA,
			'diastolica':P_TENSION_ARTERIAL_DIASTOLICA,
			'sistolica':P_TENSION_ARTERIAL_SISTOLICA,
			'respiracion':P_FRECUENCIA_RESPIRATORIA,
			'gabinete':P_LABORATORIO_GABINETE]
		
		procedimientosSignos.each{ propiedad, procedimientoSigno ->
			
			def registros = RegistroHojaEnfermeria.createCriteria().list(){
				eq("hoja.id",idHoja)
				eq("procedimiento.id",procedimientoSigno as long)
			}.each{
			
				def signoVital = result.find { s -> s.hora == it.horaregistrodiagva }
				
				if(signoVital){
					signoVital."${propiedad}" = it
				}
				else{
					signoVital = new SignoVital()
					signoVital.hora = it.horaregistrodiagva
					signoVital."${propiedad}" = it
					result << signoVital
				}
			}
		}
		
		//un valor centinela para agregar mas registros
		if(!result){
			result << new SignoVital(hora:1)
		}
		
		Collections.sort(result)
		
		result
		
	}

			
	def consultarEscalaDolorHtml(Long idHoja, Usuario usuario){
		
			def isJefeSupervisor = utilService.isJefeSupervisor(usuario)
		
			def html = """

				<input type="button" class="operacion" value="Eliminar mis registros" onclick="borrarAllDetalleDolor()"/>
				
				<div style="height:300px;overflow:auto;" class="wrapper">
				<table id="tablaDolor">					
					<thead>
					<tr>						
						<th>
							Hora
						</th>
						<th>
							Escala
						</th>
						<th>
							Usuario
						</th>
						<th>							
						</th>
					</tr>
				</thead>
				<tbody>
			"""
		
			def registros = RegistroHojaEnfermeria.createCriteria().list(){
				eq("hoja.id",idHoja)
				procedimiento{
					eq("padre.id",R_ESCALA_DOLOR as long)
				}
				
				order("horaregistrodiagva")
				
			}.each{registro->
			
			html += """
				<tr id="rowDolor${registro.id}">				
					<td>${registro.horaregistrodiagva}</td>
					<td>${registro.procedimiento.descripcion}</td>
					<td>${registro.usuario}</td>
					<td>${registro.usuario == usuario || isJefeSupervisor ?"<input type=\"button\" class=\"operacion\" value=\"Eliminar\" onclick=\"borrarDetalleDolor(${registro.id})\"/>":''}</td>
				</tr>
			"""			
			
			}
			
			html += "</tbody></table></div>"
			
			html
			
	}
	
	
	List<RegistroHojaEnfermeria> consultarEscalaDolor(Long idHoja){
		
		def registros = RegistroHojaEnfermeria.createCriteria().list(){
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_ESCALA_DOLOR as long)
			}
			
			order("horaregistrodiagva")
			
		}
		
		
		registros
		
	}
	
	
	
	def guardarEscalaDolor(String dolor, Long idHoja, Integer hora, Usuario usuario){
		
		def procedimiento = CatProcedimientoNotaEnfermeria.createCriteria().get{
			eq("padre.id",R_ESCALA_DOLOR as long)
			eq("descripcion",dolor)
			maxResults(1)
		}
		
		utilService.guardarRegistroEnfermeriaSinValor(hora ,idHoja,procedimiento.id,usuario)
		
	}
	
	def existeEscalaDolor(Long idHoja, Integer hora){
		
		boolean result = false
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("hoja.id",idHoja)
			eq("horaregistrodiagva",hora)
			procedimiento{
				eq("padre.id",R_ESCALA_DOLOR as long)
			}
			maxResults(1)
			
		}
		
		if(registro)
			result=true
		
		
		result
	}
	
	
	def borrarDetalleDolor(Long idRegistro){
		RegistroHojaEnfermeria.get(idRegistro).delete()
	}
	
	
	def borrarAllDetalleDolor(Long idHoja, Usuario usuario){
		RegistroHojaEnfermeria.createCriteria().list{
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_ESCALA_DOLOR as long)
			}			
			eq("usuario",usuario)
		}*.delete()
	}
	
	/***
	 * El rodenamiento es basico para un correcto despliegue
	 * @param idHoja
	 * @return
	 */
	def consultarDietas(Long idHoja){
		
		List<RegistroHojaEnfermeria> dietas = [
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIETA_DIETA)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIETA_MATUTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIETA_VESPERTINO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DIETA_NOCTURNO))]
		
		dietas = dietas.collect{ dieta->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",dieta.procedimiento.id)
				maxResults(1)
			}
			
			if(registro){
				dieta = registro
			}
			
			dieta
		}
		
		//Colocamos el ultimo registro como el primero
		//Collections.rotate(registros, 1)
		
		dietas
		
	}	
	
}
