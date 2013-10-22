
<input type="button" id="addIngreso" value="INGRESOS"/>

<div style="height:200px;overflow:auto;" >
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
										
					<td><g:textField id="descIngreso${i}" name="descIngreso${i}" class="descripcion" value="${ingreso.descripcion}" size="25"/></td>			
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