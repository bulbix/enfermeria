

<label for="importeTotalCirugia">Importe Total</label>
	<input type="text" id="importeTotalCirugia" style="font-weight: bold" class="costo" 
	readonly size="15" value="${resultNotasOperatoria?.importeTotal}"  />

<table>
	<thead>
		<tr>
			<th>Fecha Cirugia</th>			
			<th>Servicio</th>
			<th>Medico</th>
			<th>Cirugia</th>
			<th>Tipo</th>
			<th>Costo</th>
		</tr>
	</thead>

	<tbody>
		<g:each var="nota" in="${resultNotasOperatoria?.notas}">		
			<g:each var="operacion" in="${nota.operacionesPracticadas}">
	
				<tr>
					<td>
						${nota.fechaelaboracion.format('dd/MM/yyyy')}	
					</td>
					
					<td>
						${nota.servicio.descripcion}	
					</td>
					
					<td>
						${nota.usuario.nombre}	
					</td>
					
					<td>
						${operacion.diagnostico.descdiag}
					</td>
					
					<td>
						${operacion.tipoDiagnostico}
					</td>
					
					<td>
						<input type="text" size="9" class="costoCirugia" value="${operacion.costo}" 
						onblur="guardarCostoCirugia(${seguimientoHosp?.id},${nota.id},${operacion.diagnostico.id},this.value)" />			
					</td>
				</tr>
			</g:each>
		</g:each>
	</tbody>
</table>
