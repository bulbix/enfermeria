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
	HojaRegistroEnfermeria guardarHojaTurno(jsonHoja, Integer idUsuario){

		def hoja = new HojaRegistroEnfermeria()

		if(jsonHoja.idHoja)
			hoja = HojaRegistroEnfermeria.get(jsonHoja.idHoja)

		hoja.properties = jsonHoja
		hoja.has = jsonHoja.has == 'on'
		hoja.dm = jsonHoja.dm == 'on'
		hoja.nef = jsonHoja.nef == 'on'
		hoja.ic = jsonHoja.ic == 'on'
		hoja.ir = jsonHoja.ir == 'on'

		hoja.asignarComorbilidad()

		hoja.admision = AdmisionHospitalaria.get(jsonHoja.idAdmision)
		hoja.paciente  = Paciente.get(jsonHoja.idPaciente)
		
		hoja.save([validate:false])
		
		if(!existeTurno(hoja.id, jsonHoja.turno)){		
		
			def hojaTurno = new HojaRegistroEnfermeriaTurno(
				hoja:hoja,
				usuario:Usuario.get(idUsuario),
				turno:Turno."${jsonHoja.turno}"			
			)
			
			hojaTurno.save([validate:false])
		}
		
		hoja

	}

	/***
	 * Consulta de manera completa hoja de enfermeria
	 * @param idHoja
	 * @return hoja
	 */
	def consultarHoja(Long idHoja, String turnoActual=null){

		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idHoja)
		}
		
		if(hoja){		
			hoja.turnoActual = turnoActual
			
			hoja.signosVitales = signosVitalesService.consultarSignosVitales(idHoja)		
			hoja.dietas = signosVitalesService.consultarDietas(idHoja)
			hoja.requisitos = valoracionEnfermeriaService.consultarRequisitos(idHoja)
			
			
			hoja.rubrosValoracion = utilService.consultarCatalogoRubro(S_VALORACION)
			hoja.rubrosDiagnostico = utilService.consultarCatalogoRubro(S_DIAGNOSTICOS_INTERVENCIONES)
			hoja.rubrosIndicador = utilService.consultarCatalogoRubro(S_INDICADORES_CALIDAD)
			
			hoja.ingresos = controlLiquidosMedicamentosService.consultarIngresos(idHoja)
			hoja.medicamentos = controlLiquidosMedicamentosService.consultarMedicamentos(idHoja)
			hoja.escalaOtros = controlLiquidosMedicamentosService.consultarEscalaOtros(idHoja)
		}

		return hoja
	}
	
	
	
	def consultarHojas(Long idPaciente){
		
		def html = """

			<label>Asociar turno:</label>
			<select name="turnoAsociar" id="turnoAsociar">
				<option value="MATUTINO">MATUTINO</option>
				<option value="VESPERTINO">VESPERTINO</option>
				<option value="NOCTURNO">NOCTURNO</option>
			</select>	


			<table>
			<thead>
					<tr>						
						<th>
							Fecha Elab
						</th>
						<th>
							Matutino
						</th>
						<th>
							Vespertino
						</th>
						<th>
							Nocturno
						</th>
						<th>
							Cargar<br>
							Asociar
						</th>
						<th>
							Traslado<br>
							Paciente
						</th>
					</tr>
			</thead>
			<tbody>
		"""
		
		HojaRegistroEnfermeria.createCriteria().list{
			eq("paciente.id",idPaciente)
			order("fechaElaboracion","desc")
		}.each{ hoja->
		
			def turnos = ['MATUTINO':'','VESPERTINO':'','NOCTURNO':'']
		
			hoja.turnos.each{ 
				turnos["${it.turno}"] = it.usuario
			}
		
			html += """
				<tr>				
					<td>${hoja.fechaElaboracion.format('dd/MM/yyyy')}<td>
					<td>${turnos['MATUTINO']}</td>
					<td>${turnos['VESPERTINO']}</td>
					<td>${turnos['NOCTURNO']}</td>					
					<td><input type="button" value="ACEPTAR" 
					onclick="mostrarFirma('${hoja.id}',false,'Enfermera')"/></td>
					<td><input type="button" value="ACEPTAR" 
						onclick="mostrarFirma('${hoja.id}',false,'Traslado')"/></td>
				</tr>
			"""	
		
		}
		
		html += "</tbody></table>"
		
		html
		
	}

	def mostrarFirma(Long idHoja, boolean tieneUsuario=false, String tipoUsuario = "Enfermera"){
		
		def id = idHoja == 0?'':idHoja
		
		
		def usuario = ""
		
		
		if(tieneUsuario){
			
			usuario = """
				<tr>
					<td colspan="2"><label>Buscar usuario por RFC:</label></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="text" name="usuarioFirma" id="usuarioFirma" size="50"/>
						<input type="hidden" name="idUsuarioFirma" id="idUsuarioFirma"/>						
					</td>
				</tr>
			"""
		}
		
		def html = """

			<g:form>
				<input type="hidden" name="tipoUsuarioFirma" id="tipoUsuarioFirma" value="${tipoUsuario}"/>
				

				<table>			

				${usuario}

				<tr>
					<td colspan="2"><label>Password de firma:</label></td>
				</tr>
				<tr>
					<td colspan="2"><input type="password" name="passwordFirma" id="passwordFirma"/></td>
				<tr>
					<td><input type="button" onclick="firmarHoja('${id}')" value="Firmar Hoja"/></td>				
					<td><input type="button" onclick="jQuery('#mostrarFirma').dialog('close')" value="Cancelar"/></td>
				</tr>
				</table>
			</g:form>

		"""
		
		html
	}
	
	/***
	 * 
	 * Valida si existe la firma digital
	 * 
	 * @param idHoja
	 * @param turno
	 * @param idUsuario
	 * @param password
	 * @return si coincide
	 */
	def firmarHoja(Long idHoja,String turnoAsociar, Integer idUsuario, String password, jsonHoja,
		 Integer idUsuarioFirma = null, String tipoUsuario=null){
		
		boolean firmado = false		
		
		/*if(idHoja){//Viene de una hoja existente
			jsonHoja.idHoja = idHoja
			jsonHoja.turno = turnoAsociar
		}*/
		
		FirmaDigital firmaDigital = FirmaDigital.findWhere(passwordfirma:password?.reverse(),id:idUsuario.longValue())
		
		if(firmaDigital){
			
			if(tipoUsuario != 'Enfermera'){//Firma jefe supervisor o translado
				
				idUsuarioFirma = idUsuario
				
				def hojaTurno = HojaRegistroEnfermeriaTurno.createCriteria().get{
					eq("hoja.id",idHoja)
					eq("turno",Turno."${jsonHoja.turno}")
				}
				
				if(tipoUsuario == 'Traslado'){
				
					Integer numeroTraslado = 1;
					
					if(hojaTurno.traslado1){
						numeroTraslado=2
					}
					
					if(hojaTurno.traslado2){
						numeroTraslado=3
					}
					
					if(hojaTurno.traslado3){
						return [firmado:true,idHoja:idHoja]
					}
					
					tipoUsuario = "${tipoUsuario}${numeroTraslado}"
				}			
				
				
				hojaTurno."firma${tipoUsuario}"=true
				hojaTurno."${tipoUsuario.toLowerCase()}" = Usuario.get(idUsuarioFirma)
				hojaTurno.save([validate:false])
			}
			else{
				HojaRegistroEnfermeria hoja= guardarHojaTurno(jsonHoja, idUsuario)
				idHoja = hoja.id
			}					
			
			firmado = true			
		}
		
		[firmado:firmado,idHoja:idHoja]
	}
	
	
	boolean existeTurno(Long idHoja, String turno){
		
		def result = false
		
		def registroTurno = HojaRegistroEnfermeriaTurno.createCriteria().get{
			eq("hoja.id",idHoja)
			eq("turno",Turno."${turno}")
			maxResults(1)
		}
		
		if(registroTurno){
			result = true
		}
		
		result	
	}
	
	def existeHoja(Long idPaciente, Date fechaElaboracion){
		
		def existe = false
		
		def registro = HojaRegistroEnfermeria.createCriteria().get{
			eq("paciente.id",idPaciente)
			eq("fechaElaboracion",fechaElaboracion)
			maxResults(1)
		}
		
		if(registro){
			existe = true
		}
		
		[existe:existe,idHoja:registro?.id]
	}
	
	
	
	
	
}
