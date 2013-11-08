
<g:barraNavegacion tagAbajo="abajoLiquido"></g:barraNavegacion>


<div id="dialog-cambiarDescripcion">
	<table>
		<tr><td>
		<label>Descripcion Actual:</label><span id="descripcionOld" style="color:blue"></span>
		</td></tr>
		
		<tr><td>
		<label>Nueva Descripcion:</label><input type="text" id="descripcionNew"  value="" size="30"/>
		</td></tr>
		
		<tr><td>
		<input id="btnCambiarDescripcion" type="button" value="CAMBIAR DESCRIPCION" class="operacion"/>
		</td></tr>
	</table>
</div>

<input type="button" class="operacion" id="addIngreso" value="AGREGAR INGRESO"/>

<div class="mensaje" id="mensajeIngreso" style="color:red;font-size:20px"></div>

<div style="height:300px;overflow:auto;" >
	<table id="tablaIngresos">
		<thead>
			<tr>
				<th>Ingreso</th>
				<th>Hora<br>Inicio</th>
				<th>Hora<br>Fin</th>
				<th>Cantidad</th>
				<th>FxP M</th>
				<th>FxP V</th>
				<th>FxP N</th>
			</tr>		
		</thead>	
		<tbody>		
			<g:each in="${hojaInstance.ingresos}" var="ingreso" status="i">				
				<tr>
										
					<td>
						<input type="text" id="descIngreso${i}" class="descripcion" value="${ingreso.descripcion}" size="20"
						${ingreso.soloLectura?'readonly':''}/>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarIngreso('${i}')"/>
						
						<g:if test="${!ingreso.etiqueta}">
							<input type="button" value="CAMBIAR" class="cambiar operacion" onclick="cambiarIngreso('${i}')"/>
						</g:if>
					</td>			
					<td><input type="text" id="horaInicioIngreso${i}" class="horaInicio" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>			
					<td><input type="text" id="horaFinIngreso${i}"  class="horaFin" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>			
					<td>
						<input type="text" id="cantidadIngreso${i}" class="cantidad" size="4" onkeypress="return isNumberPointKey(event)"/>						
						<input type="button" value="AGREGAR" class="agregar operacion" onclick="guardarIngreso('${i}')"/>							
					</td>
								
					<td><input type="text" id="fxpMatutino${i}"  class="fxpMatutino" value="${ingreso.fxpM}" size="3" 
						onkeypress="return isNumberKey(event)" ${hojaInstance.turnoActual != 'MATUTINO'?'readonly':''}/></td>			
					<td><input type="text" id="fxpVespertino${i}" class="fxpVespertino" value="${ingreso.fxpV}" size="3" 
					onkeypress="return isNumberKey(event)" ${hojaInstance.turnoActual != 'VESPERTINO'?'readonly':''}/></td>			
					<td>
						<input type="text"  id="fxpNocturno${i}" class="fxpNocturno" value="${ingreso.fxpN}" size="3" 
						onkeypress="return isNumberKey(event)" ${hojaInstance.turnoActual != 'NOCTURNO'?'readonly':''}/>
						<input type="button" class="operacion" value="GUARDAR FxP" class="agregarfaltante" onclick="guardarFaltante('${i}')"/>		
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>

<input type="button" class="operacion" id="addEgreso" value="EGRESOS"/>
<div class="mensaje" id="mensajeEgreso" style="color:red;font-size:20px"></div>

<div>
	<table id="tablaEgresos">
		<thead>
			<tr>
				<th>Egreso</th>
				<th>Hora<br>Inicio</th>
				<th>Hora<br>Fin</th>
				<th>Registro</th>
				<th></th>
				<th></th>				
			</tr>		
		</thead>	
		<tbody>		
			<g:each in="${hojaInstance.egresos}" var="egreso" status="i">				
				<tr>
										
					<td>
						<input type="text" readonly id="descEgreso${i}"  class="descripcion" value="${egreso.descripcion}" size="15"/>												
					</td>			
					<td><input type="text" id="horaInicioEgreso${i}"  class="horaInicio" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>			
					<td><input type="text" id="horaFinEgreso${i}"  class="horaFin" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>			
					<td>
					<g:if test="${['Diuresis','Vomito'].contains(egreso.descripcion)}">
						<input type="text" id="cantidadEgreso${i}" class="cantidad" size="5" onkeypress="return isNumberKey(event)"/>
						<label>No cuantificado</label><g:checkBox id="cuantificarEgreso${i}" name="cantidadEgreso${i}"/>				
					</g:if>
					<g:else>
						<g:if test="${egreso.descripcion=='Fuga'}">							
							<g:radioGroup id="cantidadEgreso${i}" name="cantidadEgreso${i}" 
								 labels="['+','++','+++']" values="['+','++','+++']">
								${it.label} ${it.radio}
							</g:radioGroup>
						</g:if>
						<g:elseif test="${egreso.descripcion=='Evacuacion'}">
							<input type="text" id="cantidadEgreso${i}" class="cantidadEgreso" size="5"/>	
						</g:elseif>
						<g:else>
							<input type="text" id="cantidadEgreso${i}" class="cantidadEgreso" size="5" onkeypress="return isNumberKey(event)"/>						
						</g:else>
											
					</g:else>
					
					</td>
					
					<td>
						<input type="button" value="AGREGAR" class="agregar operacion" onclick="guardarEgreso('${i}')"/>	
					</td>		
					<td>						
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarEgreso('${i}')"/>
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>


<input type="button" class="operacion" id="addMedicamento" value="AGREGAR MEDICAMENTO"/>
<div class="mensaje" id="mensajeMedicamento" style="color:red;font-size:20px"></div>

<div style="height:300px;overflow:auto;" >
	<table id="tablaMedicamentos">
		<thead>
			<tr>
				<th>Medicamento</th>
				<th>Hora</th>
				<th>Dosis</th>
				<th></th>
				<th></th>
				<th></th>				
			</tr>		
		</thead>	
		<tbody>		
			<g:each in="${hojaInstance.medicamentos}" var="medicamento" status="i">				
				<tr>
										
					<td>
						<input type="text" id="descMedicamento${i}" class="descripcion" value="${medicamento.descripcion}" size="25" 
						${medicamento.soloLectura?'readonly':''}/>						
					</td>			
					<td><input type="text" id="horaInicioMedicamento${i}" class="horaInicio" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>		
								
					<td>
						<input type="text" id="cantidadMedicamento${i}" class="cantidad" size="3"/>
					</td>
								
								
					<td>				
						<input type="button" value="AGREGAR" class="agregar operacion" onclick="guardarMedicamento('${i}')"/>						
					</td>
					<td>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarMedicamento('${i}')"/>
						
					</td>
					<td>
						<input type="button" value="CAMBIAR" class="cambiar operacion" onclick="cambiarMedicamento('${i}')"/>
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>


<input type="button" class="operacion" id="addEscalaOtro" value="AGREGAR ESCALA GLASGOW Y OTRO"/>
<div class="mensaje" id="mensajeEscalaOtro" style="color:red;font-size:20px"></div>

<div style="height:300px;overflow:auto;" >
	<table id="tablaEscalaOtros">
		<thead>
			<tr>
				<th>Escala Glasgow Otro</th>
				<th>Hora</th>
				<th>Dosis</th>
				<th></th>
				<th></th>
				<th></th>				
			</tr>		
		</thead>	
		<tbody>		
			<g:each in="${hojaInstance.escalaOtros}" var="escalaOtro" status="i">				
				<tr>
										
					<td>
						<input type="text" id="descEscalaOtro${i}" class="descripcion" value="${escalaOtro.descripcion}" size="25" 
						${escalaOtro.soloLectura?'readonly':''} />						
					</td>			
					<td><input type="text" id="horaInicioEscalaOtro${i}"  class="horaInicio" value="1" size="2" onkeypress="return isNumberKey(event)"/></td>		
								
					<td>
						<input type="text" id="cantidadEscalaOtro${i}" class="cantidad" size="3"/>
					</td>
								
								
					<td>				
						<input type="button" value="AGREGAR" class="agregar operacion" onclick="guardarEscalaOtro('${i}')"/>						
					</td>
					<td>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarEscalaOtro('${i}')"/>
						
					</td>
					
					<g:if test="${!escalaOtro.etiqueta}">
					<td>
						<input type="button" value="CAMBIAR" class="cambiar operacion" onclick="cambiarEscalaOtro('${i}')"/>
					</td>
					</g:if>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>

<a name="abajoLiquido"></a>
