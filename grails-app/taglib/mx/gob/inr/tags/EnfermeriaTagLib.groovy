package mx.gob.inr.tags

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.HojaRegistroClinicoService
import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class EnfermeriaTagLib {
	
	
	HojaRegistroClinicoService hojaRegistroClinicoService
	
	def tablaValoracion = { attrs, body ->
		
		def idHoja = attrs.idhoja as long
		def idRubro = attrs.idrubro as long
		def titulo = attrs.titulo
		def turno = attrs.turno
		
		
		def result = new StringBuffer()
		
		result << """<table>
					<tr>
						<th colspan="2">
							${titulo.trim()}
						</th>					
					</tr>
			"""
	
		
		
		def registros = CatProcedimientoNotaEnfermeria.createCriteria().list {
			eq("padre.id",idRubro)			
		}.each {
		
			def checks = hojaRegistroClinicoService.consultarCheckTabla(idHoja,it.id)	
			
			if(it.descripcion.trim() in ["Otro:","Etapa de duelo","Religion","Practicas religiosas relacionadas a la salud"]){
				result << """
								<tr>
									<td><label>${it.descripcion.trim()}</label></td>
									<td>
										<input type="text" value="wii"/>
									</td>
								</tr>
						"""
			}
			else{
				result << """
								<tr>
									<td>										
										<label>${it.descripcion.trim()}</label>
									</td>
									<td>
										<table>
										<tr>
										<td><input type="checkbox" name="turnocheckM" ${checks[0]=='1'?'checked':''} onclick="guardarCheckTabla(${idHoja},${it.id},'MATUTINO',this.checked)">M</td>
										<td><input type="checkbox" name="turnocheckV" ${checks[1]=='1'?'checked':''} onclick="guardarCheckTabla(${idHoja},${it.id},'VESPERTINO',this.checked)">V</td>
										<td><input type="checkbox" name="turnocheckN" ${checks[2]=='1'?'checked':''} onclick="guardarCheckTabla(${idHoja},${it.id},'NOCTURNO',this.checked)">N</td>
										</tr>
										</table>
									</td>
								</tr>
						"""
			}
						
		}		
		
		result << "</table>"
		
		if(idRubro != R_REQUISITOS)
			out << result
		
		
	}

}
