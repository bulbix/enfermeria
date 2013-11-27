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
		<g:each var="nota" in="${notasOperatoria}">		
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
					<td style="white-space: nowrap">
						$<input type="text" size="9" />			
					</td>
				</tr>
			</g:each>
		</g:each>
	</tbody>
</table>
