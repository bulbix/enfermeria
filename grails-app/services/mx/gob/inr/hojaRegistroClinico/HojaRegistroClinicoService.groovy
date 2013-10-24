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
	def consultarHoja(Long idHoja, String turnoActual){

		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idHoja)
		}
		
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
		def html = """

			<g:form>
				<label>Password de firma:</label>
				<input type="password" name="passwordFirma" id="passwordFirma"/>				
				<input type="button" onclick="firmarHoja('$idHoja')" value="Firmar Hoja"/>				
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
	boolean firmarHoja(Long idHoja, String turno, Integer idUsuario, String password){
		
		boolean result = false
		
		FirmaDigital firmaDigital = FirmaDigital.findWhere(passwordfirma:password?.reverse(),id:idUsuario)
		
		if(firmaDigital){
			def hojaTurno = new HojaRegistroEnfermeriaTurno(
				hoja:HojaRegistroEnfermeria.get(idHoja),
				usuario:Usuario.get(idUsuario),
				turno:Turno."${turno}"			
			)
				
			hojaTurno.save([validate:false])
			result = true			
		}
		
		result
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
	
	
	
	
	
}
