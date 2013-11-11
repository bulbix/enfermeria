package mx.gob.inr.tags

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.HojaRegistroClinicoService
import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class EnfermeriaTagLib {
	
	
	def utilService	
	
	def hojaRegistroClinicoService
	
	def springSecurityService
	
	def tablaEnfermeria = { attrs, body ->
		
		def idHoja = null
		
		if(attrs.idhoja)
			idHoja = attrs.idhoja as long
		
		def idRubro = attrs.idrubro as long
		def titulo = attrs.titulo
		def turno = attrs.turno
		def mostrar = attrs.mostrar as boolean
		def tipo = attrs.tipo
		
		//Esta validacion es temporal solo en produccion
		if(idRubro == R_PREVENSION_CAIDAS)
			return
		
		
		if(!mostrar || !idHoja)
			return
		
		
		def result = new StringBuffer()
		
		
		if(tipo=="check"){			
			result << """
				<div class="element">
				<input type="button" class="operacion" value="Seleccionar Todo" onclick="seleccionarChecks('turnocheck${turno.charAt(0)}${idRubro}',true)"/>
				<input type="button" class="operacion" value="Quitar Todo" onclick="seleccionarChecks('turnocheck${turno.charAt(0)}${idRubro}',false)"/>
			"""
		}
		else if(tipo=="radio"){
			result << """
				<div class="element">
				<input type="button" class="operacion" value="Todo SI" onclick="seleccionarRadios('radioSi${idRubro}','SI')"/>
				<input type="button" class="operacion" value="Todo NO" onclick="seleccionarRadios('radioNo${idRubro}','NO')"/>
				<input type="button" class="operacion" value="Limpiar Todo" onclick="seleccionarRadios('botonLimpieza${idRubro}','')"/>
			"""			
		}
		
		
		result << """
				
				<table class="tablaEnfermeria">
					<tr>						
						<th colspan="2">
							${titulo.trim()}
						</th>					
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
					maxResults(1)
				}
				
				result << """
								<tr>
									<td colspan="2" class="celdaIzquierda">
										<label>${procedimiento.descripcion.trim()}</label>
										<input type="text" value="${text ?: ''}" onblur="guardarTextTabla(${idHoja},${procedimiento.id},this.value)"  />
									</td>									
								</tr>
						"""
			}
			else{		
				
				
				if(tipo=="check"){
					
					result << """ <tr><td class="celdaIzquierdaCheck">${procedimiento.descripcion.trim()}</td> """
					
					def checks = utilService.consultarRegistroTabla(idHoja,procedimiento.id)
					
					result << """
									<td class="celdaDerechaCheck">										
										<input type="checkbox" name="turnocheckM${idRubro}" ${checks[0]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'MATUTINO',this.checked)" ${turno!='MATUTINO'?'disabled':''}><label>M</label>
										<input type="checkbox" name="turnocheckV${idRubro}" ${checks[1]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'VESPERTINO',this.checked)" ${turno!='VESPERTINO'?'disabled':''}><label>V</label>
										<input type="checkbox" name="turnocheckN${idRubro}" ${checks[2]=='1'?'checked':''} onchange="guardarCheckTabla(${idHoja},${procedimiento.id},'NOCTURNO',this.checked)" ${turno!='NOCTURNO'?'disabled':''}><label>N</label>
									</td>
						"""
						
				}
				else if(tipo=="radio"){
					
					result << """ <tr><td class="celdaIzquierdaRadio">${procedimiento.descripcion.trim()}</td> """					
					
					def radio = utilService.consultarRegistroTabla(idHoja,procedimiento.id).trim()					
					
					result << """
									<td class="celdaDerechaRadio">										
										<input type="radio" class="radioSi${idRubro}" name="radio${procedimiento.id}" ${radio=='SI'?'checked':''} onclick="guardarRadioTabla(${idHoja},${procedimiento.id},'SI')">Si
										<input type="radio" class="radioNo${idRubro}" name="radio${procedimiento.id}" ${radio=='NO'?'checked':''} onclick="guardarRadioTabla(${idHoja},${procedimiento.id},'NO')">No										
										<input type="button" class="operacion" name="botonLimpieza${idRubro}" value="Limpiar" onclick="borrarRadioTabla(${idHoja},${procedimiento.id},'radio${procedimiento.id}')"/>
									</td>
						"""
					
				}
				
				
				result << "</tr>"
			}
						
		}		
		
		result << "</table></div>"
		
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
	
	
	def barraNavegacion= { attrs, body ->		
		
		def html = """

		<div style="position:fixed;background-color:rgb(190,214,248);top:0;left:0;z-index:99">	
			<div class="nav" role="navigation">
					<ul>
						<li>
							<a href="#arriba" class="arriba">IR ARRIBA</a>
						</li>
						<li>
							<a href="#${attrs.tagAbajo}" class="abajo">IR ABAJO</a>
						</li>
					</ul>
			</div>
		</div>
		"""		
		out << html
	}
	
	def usuarioActual={ attrs, body ->
		
		def html = "Inicio sesion como: " + springSecurityService.currentUser.nombre		
		out << html
	}
	
	
	
}
