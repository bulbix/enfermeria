package mx.gob.inr.hojaRegistroClinico

import java.util.List;

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

	def guardarSignosVitales(Long idHoja, Integer idUsuario, List horas,List temperaturaList, List cardiacaList,
		List sistolicaList, List diastolicaList, List respiracionList, List gabineteList){
		
		horas.eachWithIndex { hora, index ->
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
			idHoja,P_TEMEPRATURA,idUsuario,temperaturaList[index])
			
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_FRECUENCIA_CARDIACA,idUsuario,cardiacaList[index])
			
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_TENSION_ARTERIAL_SISTOLICA,idUsuario,sistolicaList[index])
			
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_TENSION_ARTERIAL_DIASTOLICA,idUsuario,diastolicaList[index])
			
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_FRECUENCIA_RESPIRATORIA,idUsuario,respiracionList[index])
			
			utilService.guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_LABORATORIO_GABINETE,idUsuario,gabineteList[index])
		}
		
	}
		
	def consultarEscalaDolorHtml(Long idHoja){
		
			def html = """

				<table>
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
							Eliminar
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
					<td>${registro.horaregistrodiagva}<td>
					<td>${registro.procedimiento.descripcion}</td>
					<td>${registro.usuario}</td>
					<td><input type="button" value="Eliminar" onclick="borrarDetalleDolor(${registro.id})"/></td>
				</tr>
			"""			
			
			}
			
			html += "</tbody></table>"
			
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
	
	
	
	def guardarEscalaDolor(String dolor, Long idHoja, Integer hora, Integer idUsuario){
		
		def procedimiento = CatProcedimientoNotaEnfermeria.createCriteria().get{
			eq("padre.id",R_ESCALA_DOLOR as long)
			eq("descripcion",dolor)
		}
		
		utilService.guardarRegistroEnfermeriaSinValor(hora ,idHoja,procedimiento.id,idUsuario)
		
	}
	
	
	def borrarDetalleDolor(Long idRegistro){
		RegistroHojaEnfermeria.get(idRegistro).delete()
	}
	
	/***
	 * El rodenamiento es basico para un correcto despliegue
	 * @param idHoja
	 * @return
	 */
	def consultarDietas(Long idHoja){
		
		def registros = RegistroHojaEnfermeria.createCriteria().list(){
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_DIETA as long)
				order("id")
			}
		}
		
		//Colocamos el ultimo registro como el primero
		Collections.rotate(registros, 1)
		
		registros
		
	}
		
	def guardarDietas(Long idHoja, Integer idUsuario, List dietas, List horaDietas){
		
		def procedimientos = [P_DIETA_DIETA,P_DIETA_MATUTINO,P_DIETA_VESPERTINO,P_DIETA_NOCTURNO];
				
		procedimientos.eachWithIndex { procedimiento, index->
			utilService.guardarRegistroEnfermeria(horaDietas[index] as int,idHoja,procedimiento,idUsuario, dietas[index],true)
		}
		
	}	
	
}
