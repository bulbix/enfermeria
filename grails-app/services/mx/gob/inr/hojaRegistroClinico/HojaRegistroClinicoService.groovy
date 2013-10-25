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
					onclick="mostrarFirma('${hoja.id}')"/></td>
					<td><input type="button" value="ACEPTAR" 
					onclick=""/></td>
				</tr>
			"""	
		
		}
		
		html += "</tbody></table>"
		
		html
		
	}

	def mostrarFirma(Long idHoja){
		
		def id = idHoja == 0?'':idHoja
		
		def html = """

			<g:form>
				<label>Password de firma:</label>
				<input type="password" name="passwordFirma" id="passwordFirma"/>				
				<input type="button" onclick="firmarHoja('${id}')" value="Firmar Hoja"/>				
				<input type="button" onclick="jQuery('#mostrarFirma').dialog('close')" value="Cancelar"/>		
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
	def firmarHoja(Long idHoja,String turnoAsociar, Integer idUsuario, String password, jsonHoja){
		
		boolean firmado = false		
		
		if(idHoja){//Viene de una hoja existente
			jsonHoja.idHoja = idHoja
			jsonHoja.turno = turnoAsociar
		}
		
		FirmaDigital firmaDigital = FirmaDigital.findWhere(passwordfirma:password?.reverse(),id:idUsuario.longValue())
		
		if(firmaDigital){					
			HojaRegistroEnfermeria hoja= guardarHojaTurno(jsonHoja, idUsuario)
			idHoja = hoja.id
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
