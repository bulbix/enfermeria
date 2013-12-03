
<label for="importeTotal">Importe Total</label>
	<input type="text" id="importeTotal" style="font-weight: bold" class="costo" 
	readonly size="15" value="${resultTipoAgendas?.importeTotal}"  />

<div class="accordion">

	<g:each var="tipoAgenda" in="${resultTipoAgendas?.tiposAgenda}">
		<h3>${tipoAgenda?.descripcion}</h3>		
		<div>		
			<table>
				<thead>
					<tr>
						<th>Fecha Cita</th>
						<th>Hora Cita</th>
						<th>Estudio</th>
						<th>Costo</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td style="font-weight:bold">Importe</td>
						<td><input type="text" style="font-weight: bold" class="costo" 
								readonly size="15" value="${tipoAgenda?.importe}" /></td>					
					</tr>
				</tfoot>
				
				<tbody>					
					<g:each var="agenda" in="${tipoAgenda?.agendas}">
						<tr>
							<td>${agenda?.fechacita?.format('dd/MM/yyyy')}</td>
							<td>${agenda?.horacita?.format('HH:mm')}</td>
							<td>${agenda?.estudio?.desestudio}</td>
							<td class="costo">${agenda?.costo}</td>					
						</tr>
					</g:each>
				</tbody>
			</table>						
		</div>	
	</g:each>
	
	
</div>