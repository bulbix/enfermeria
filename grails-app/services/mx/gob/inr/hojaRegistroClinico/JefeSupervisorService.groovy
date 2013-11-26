package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.seguridad.FirmaDigital
import mx.gob.inr.seguridad.Usuario;
import mx.gob.inr.utils.Turno
import mx.gob.inr.utils.Paciente

class JefeSupervisorService {
	
	def hojaRegistroClinicoService
	def utilService
	
	def consultarHojas(Long idPaciente, String turno){
		
		def html = """			
			
			<div style="height:500px;overflow:auto;" class="wrapper" >
			<table id="tablaHojas">
			<thead>			
					<tr>						
						<th>
							Fecha<br>Elaboracion
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
							Generar<br>
							Hoja
						</th>
						<th>
							Firmar<br>
							Jefe
						</th>
						<th>
							Firmar<br>
							Supervisor
						</th>
					</tr>			
			</thead><tbody>

		"""
		
		HojaRegistroEnfermeria.createCriteria().list{
			eq("paciente.id",idPaciente)
			order("fechaElaboracion","desc")
		}.each{ hoja->		
		
			html += """
				<tr>				
					<td>${hoja.fechaElaboracion.format('dd/MM/yyyy')}</td>
					<td>
						${hoja?.turnoMatutino?.jefe?"<a class=\"jefe\" title=\"Jef@:${hoja?.turnoMatutino?.jefe}\"><img src=\"/enfermeria/images/icons/seguridad.gif\"/></a>":''}
						${hoja?.turnoMatutino?.supervisor?"<a class=\"supervisor\" title=\"Supervis@r:${hoja?.turnoMatutino?.supervisor}\"><img src=\"/enfermeria/images/icons/usuarios.gif\"/></a>":''}						
						
						<ul style="margin:0;padding:0;list-style-type:none">
							<li><label style="color:blue">${hoja?.turnoMatutino?.usuario?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoMatutino?.traslado1?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoMatutino?.traslado2?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoMatutino?.traslado3?:''}</label></li>
						</ul>
					</td>
					<td>
						${hoja?.turnoVespertino?.jefe?"<a class=\"jefe\" title=\"Jef@:${hoja?.turnoVespertino?.jefe}\"><img src=\"/enfermeria/images/icons/seguridad.gif\"/></a>":''}
						${hoja?.turnoVespertino?.supervisor?"<a class=\"supervisor\" title=\"Supervis@r:${hoja?.turnoVespertino?.supervisor}\"><img src=\"/enfermeria/images/icons/usuarios.gif\"/></a>":''}

						<ul style="margin:0;padding:0;list-style-type:none">
							<li><label style="color:blue">${hoja?.turnoVespertino?.usuario?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoVespertino?.traslado1?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoVespertino?.traslado2?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoVespertino?.traslado3?:''}</label></li>
						</ul>				
					</td>
					<td>
						${hoja?.turnoNocturno?.jefe?"<a class=\"jefe\" title=\"Jef@:${hoja?.turnoNocturno?.jefe}\"><img src=\"/enfermeria/images/icons/seguridad.gif\"/></a>":''}
						${hoja?.turnoNocturno?.supervisor?"<a class=\"supervisor\" title=\"Supervis@r:${hoja?.turnoNocturno?.supervisor}\"><img src=\"/enfermeria/images/icons/usuarios.gif\"/></a>":''}

						<ul style="margin:0;padding:0;list-style-type:none">
							<li><label style="color:blue">${hoja?.turnoNocturno?.usuario?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoNocturno?.traslado1?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoNocturno?.traslado2?:''}</label></li>
							<li><label style="color:red">${hoja?.turnoNocturno?.traslado3?:''}</label></li>
						</ul>				
					</td>

					<td><input type="button" value="GENERAR" onclick="imprimirHoja(${hoja.id})"/></td>

			"""			
			
			
			if(hojaRegistroClinicoService.existeTurno(hoja.id, turno)){
								
				if(!hojaRegistroClinicoService.existeFirma(hoja.id, "Jefe",turno)){
					html += """
					<td><input type="button" value="FIRMAR" onclick="mostrarFirma('${hoja.id}','${turno}','Jefe','${hoja.fechaElaboracion.format('dd/MM/yyyy')}')"/></td>
				"""
				}
				else{
					html+="<td></td>"
				}
				
			}
			else{
				html+="<td></td>"				
			}
			
			
			if(hojaRegistroClinicoService.existeTurno(hoja.id, turno)){			
				if(!hojaRegistroClinicoService.existeFirma(hoja.id, "Supervisor",turno)){
					html += """
						<td><input type="button" value="FIRMAR" onclick="mostrarFirma('${hoja.id}','${turno}','Supervisor','${hoja.fechaElaboracion.format('dd/MM/yyyy')}')"/></td>
					"""
				}
				else{
					html+="<td></td>"
				}
			}
			else{
				html+="<td></td>"
			}

									
					
			html += "</tr>"
				
		
		}
		
		html += "</tbody></table></div>"
		
		html
		
	}

	
	def mostrarFirma(Long idHoja, String tipoUsuario, String turnoAsociar, String fechaElaboracion){			
		
		
		def html = """

			<g:form>				

				<table>				

				<tr>
					<td colspan="2"><label>Password de firma:</label></td>
				</tr>
				<tr>
					<td colspan="2"><input type="password" name="passwordFirma" id="passwordFirma"/></td>
				<tr>
					<td><input type="button" id="btnFirmarHoja" onclick="firmarHoja(${idHoja},'${turnoAsociar}','${tipoUsuario}','${fechaElaboracion}')" value="Firmar Hoja"/></td>				
					<td><input type="button" onclick="jQuery('#mostrarFirma').dialog('close')" value="Cancelar"/></td>
				</tr>
				</table>
			</g:form>

		"""
		
		html
	}
	
	def firmarHoja(Long idHoja,String asociarTurno, Usuario usuario, String password, String tipoUsuario){
	
		boolean firmado = false
	
		FirmaDigital firmaDigital = FirmaDigital.findWhere(passwordfirma:password?.reverse(),
		id:usuario.id)
	
		if(firmaDigital){
	
			def hojaTurno = HojaRegistroEnfermeriaTurno.createCriteria().get{
				eq("hoja.id",idHoja)
				eq("turno",Turno."${asociarTurno}")
				maxResults(1)
			}
	
	
			hojaTurno."firma${tipoUsuario}"=true
			hojaTurno."${tipoUsuario.toLowerCase()}" = usuario
			hojaTurno.save([validate:false])
	
	
			firmado = true
		}
	
		firmado
	}
	
	
	def consultarPacientes(Long idArea){
		
		utilService.consultarPacientes("", idArea).each{ paciente ->
			
			paciente['tieneHoja'] = existeHojasByPaciente(paciente.id)
		}	 
		
	}
	
	/***
	 * Comprueba si almenos un paciente tiene hojas
	 * @param idPaciente
	 * @return
	 */
	boolean existeHojasByPaciente(Long idPaciente){
		
		def result = false
		
		if(HojaRegistroEnfermeria.findByPaciente(Paciente.read(idPaciente))){
			result = true
		}
		
		result
	}
	
}
