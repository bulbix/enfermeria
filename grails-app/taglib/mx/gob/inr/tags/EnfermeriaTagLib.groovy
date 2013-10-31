package mx.gob.inr.tags

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.HojaRegistroClinicoService
import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class EnfermeriaTagLib {
	
	
	def utilService	
	
	def hojaRegistroClinicoService
	
	def tablaEnfermeria = { attrs, body ->
		
		def idHoja = null
		
		if(attrs.idhoja)
			idHoja = attrs.idhoja as long
		
		def idRubro = attrs.idrubro as long
		def titulo = attrs.titulo
		def turno = attrs.turno
		def mostrar = attrs.mostrar as boolean
		def tipo = attrs.tipo
		
		if(!mostrar || !idHoja)
			return
		
		
		def result = new StringBuffer()
		
		result << """
				<input type="button" value="Seleccionar Todo" onclick="seleccionarChecks('turnocheck${turno.charAt(0)}${idRubro}',true)"/>
				<input type="button" value="Quitar Todo" onclick="seleccionarChecks('turnocheck${turno.charAt(0)}${idRubro}',false)"/>
				<table>
					<tr>						
						<th colspan="2">
							${titulo.trim()}
						</th>					
					</tr>
					<tr>
						<td>
							
						</td>
						<td>
							
						</td>						
					</tr>
			"""
	
		
		
		CatProcedimientoNotaEnfermeria.createCriteria().list {
			eq("padre.id",idRubro)			
		}.each { procedimiento ->
		
				
			
			if(procedimiento.descripcion.trim() in ["Otro:","Etapa de duelo","Religion","Practicas religiosas relacionadas a la salud"]){
				
				def text = RegistroHojaEnfermeria.createCriteria().get{
					
					projections{
						property("otro")
					}
					
					eq("hoja.id",idHoja)
					eq("procedimiento.id",procedimiento.id)
				}
				
				result << """
								<tr>
									<td><label>${procedimiento.descripcion.trim()}</label></td>
									<td>
										<input type="text" value="${text ?: ''}" onblur="guardarTextTabla(${idHoja},${procedimiento.id},this.value)" />
									</td>
								</tr>
						"""
			}
			else{		
				
				result << """
								<tr>
									<td>										
										<label>${procedimiento.descripcion.trim()}</label>
									</td>
						"""
				
				if(tipo=="check"){
					
					def checks = utilService.consultarRegistroTabla(idHoja,procedimiento.id)
					
					result << """
									<td>
										<table>
										<tr>
										<td><input type="checkbox" name="turnocheckM${idRubro}" ${checks[0]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'MATUTINO',this.checked)" ${turno!='MATUTINO'?'disabled':''}><label>M</label></td>
										<td><input type="checkbox" name="turnocheckV${idRubro}" ${checks[1]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'VESPERTINO',this.checked)" ${turno!='VESPERTINO'?'disabled':''}><label>V</label></td>
										<td><input type="checkbox" name="turnocheckN${idRubro}" ${checks[2]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'NOCTURNO',this.checked)" ${turno!='NOCTURNO'?'disabled':''}><label>N</label></td>
										</tr>
										</table>
									</td>
						"""
						
				}
				else if(tipo=="radio"){
					
					
					def radio = utilService.consultarRegistroTabla(idHoja,procedimiento.id).trim()
					
					
					result << """
									<td>
										<table>
										<tr>
										<td><input type="radio" id="turnoRadio${procedimiento.id}" name="turnoRadio${procedimiento.id}" ${radio=='SI'?'checked':''} onclick="guardarRadioTabla(${idHoja},${procedimiento.id},'SI')">Si</td>
										<td><input type="radio" id="turnoRadio${procedimiento.id}" name="turnoRadio${procedimiento.id}" ${radio=='NO'?'checked':''} onclick="guardarRadioTabla(${idHoja},${procedimiento.id},'NO')">No</td>										
										<td><input type="button" value="Limpiar" onclick="borrarRadioTabla(${idHoja},${procedimiento.id},'turnoRadio${procedimiento.id}')"/>
										</tr>
										</table>
									</td>
						"""
					
				}
				
				
				result << "</tr>"
			}
						
		}		
		
		result << "</table>"
		
		out << result
		
		
	}

	
	
	def existeFirma= { attrs, body ->
		
		def idHoja = attrs.idHoja
		def tipoUsuario = attrs.tipoUsuario
		def turno = attrs.turno
		
		
		def result = hojaRegistroClinicoService.existeFirma(idHoja as long, tipoUsuario,turno)
		
		if(!result){
			out << body()
		}	
		
	}
	
	
}
