
<input type="button" id="addIngreso" value="INGRESOS"/>

<div style="height:450px;overflow:auto;" >
	<table id="tablaIngresos">
		<thead>
			<tr>
				<th></th>
				<th>Hora Inicio</th>
				<th>Hora Fin</th>
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
						<g:textField id="descIngreso${i}" name="descIngreso${i}" class="descripcion" value="${ingreso.descripcion}" size="25"/>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarIngreso('${i}')"/>
						<input type="button" value="CAMBIAR" class="cambiar" onclick="cambiarIngreso('${i}')"/>
					</td>			
					<td><g:textField id="horaInicio${i}" name="horaInicio${i}" class="horaInicio" value="1" size="5"/></td>			
					<td><g:textField id="horaFin${i}" name="horaFin${i}"  class="horaFin" value="1" size="5"/></td>			
					<td>
						<g:textField id="cantidadIngreso${i}" name="cantidadIngreso${i}" class="cantidadIngreso" size="5"/>						
						<input type="button" value="AGREGAR" class="agregar" onclick="guardarIngreso('${i}')"/>							
					</td>
								
					<td><g:textField id="fxpMatutino${i}" name="fxpMatutino${i}" class="fxpMatutino" value="${ingreso.fxpM}" size="5"/></td>			
					<td><g:textField id="fxpVespertino${i}" name="fxpVespertino${i}" class="fxpVespertino" value="${ingreso.fxpV}" size="5"/></td>			
					<td>
						<g:textField  id="fxpNocturno${i}" name="fxpNocturno${i}" class="fxpNocturno" value="${ingreso.fxpN}" size="5"/>
						<input type="button" value="GUARDAR" class="agregarfaltante" onclick="guardarFaltante('${i}')"/>		
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>

<input type="button" id="addEgreso" value="EGRESOS"/>

<div>
	<table id="tablaEgresos">
		<thead>
			<tr>
				<th></th>
				<th>Hora Inicio</th>
				<th>Hora Fin</th>
				<th>Registro</th>
				<th></th>
				<th></th>				
			</tr>		
		</thead>	
		<tbody>		
			<g:each in="${hojaInstance.egresos}" var="egreso" status="i">				
				<tr>
										
					<td>
						<g:textField id="descEgreso${i}" name="descEgreso${i}" class="descripcion" value="${egreso}" size="10"/>												
					</td>			
					<td><g:textField id="horaInicio${i}" name="horaInicio${i}" class="horaInicio" value="1" size="5"/></td>			
					<td><g:textField id="horaFin${i}" name="horaFin${i}"  class="horaFin" value="1" size="5"/></td>			
					<td>
					<g:if test="${['Diuresis','Vomito'].contains(egreso)}">
						<g:textField id="cantidadEgreso${i}" name="cantidadEgreso${i}" class="cantidadEgreso" size="5"/>
						<label>No cuantificado</label><g:checkBox name="" value=""/>				
					</g:if>
					<g:else>
						<g:if test="${egreso=='Fuga'}">
							
							<g:radioGroup name="fugas" labels="['+','++','+++']" values="[1,2,3]">
								${it.label} ${it.radio}
							</g:radioGroup>
						</g:if>
						<g:else>
							<g:textField id="cantidadEgreso${i}" name="cantidadEgreso${i}" class="cantidadEgreso" size="5"/>						
						</g:else>
											
					</g:else>
					
					</td>
					
					<td>
						<input type="button" value="AGREGAR" class="agregar" onclick="guardarEgreso('${i}')"/>	
					</td>		
					<td>						
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarEgreso('${i}')"/>
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>


<input type="button" id="addMedicamento" value="MEDICAMENTOS"/>

<div style="height:450px;overflow:auto;" >
	<table id="tablaMedicamentos">
		<thead>
			<tr>
				<th></th>
				<th>Hora Inicio</th>
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
						<g:textField id="descMedicamento${i}" name="descMedicamento${i}" class="descripcion" value="${medicamento}" size="25"/>						
					</td>			
					<td><g:textField id="horaInicio${i}" name="horaInicio${i}" class="horaInicio" value="1" size="5"/></td>		
								
					<td>
						<g:textField id="cantidadMedicamento${i}" name="cantidadMedicamento${i}" class="cantidadMedicamento" size="5"/>
					</td>
								
								
					<td>				
						<input type="button" value="AGREGAR" class="agregar" onclick="guardarMedicamento('${i}')"/>						
					</td>
					<td>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarMedicamento('${i}')"/>
						
					</td>
					<td>
						<input type="button" value="CAMBIAR" class="cambiar" onclick="cambiarMedicamento('${i}')"/>
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>


<input type="button" id="addEscalaOtro" value="ESCALA GLASGOW Y OTROS"/>

<div style="height:450px;overflow:auto;" >
	<table id="tablaEscalaOtros">
		<thead>
			<tr>
				<th></th>
				<th>Hora Inicio</th>
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
						<g:textField id="descEscalaOtro${i}" name="descEscalaOtro${i}" class="descripcion" value="${escalaOtro}" size="25"/>						
					</td>			
					<td><g:textField id="horaInicio${i}" name="horaInicio${i}" class="horaInicio" value="1" size="5"/></td>		
								
					<td>
						<g:textField id="cantidadEscalaOtro${i}" name="cantidadEscalaOtro${i}" class="cantidadEscalaOtro" size="5"/>
					</td>
								
								
					<td>				
						<input type="button" value="AGREGAR" class="agregar" onclick="guardarEscalaOtro('${i}')"/>						
					</td>
					<td>
						<input type="button" value="MOSTRAR" class="mostrar" onclick="mostrarEscalaOtro('${i}')"/>
						
					</td>
					<td>
						<input type="button" value="CAMBIAR" class="cambiar" onclick="cambiarEscalaOtro('${i}')"/>
					</td>					
				</tr>			
			</g:each>		
		</tbody>
	</table>
</div>


