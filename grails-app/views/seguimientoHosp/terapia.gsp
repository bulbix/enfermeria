
<label for="importeTotalTerapia">Importe Total</label>
	<input type="text" id="importeTotalTerapia" style="font-weight: bold" class="costo" 
	readonly size="15" value="${resultTipoAgendasTerapia?.importeTotal}"  />

<div class="accordion">

	<g:each var="tipoAgenda" in="${resultTipoAgendasTerapia?.tiposAgenda}">
		<h3>${tipoAgenda?.descripcion.capitalize()}</h3>		
		<div>		
			<table>
				<thead>
					<tr>
						<th>Fecha Cita</th>
						<th>Hora Cita</th>
						<th>Terapia</th>
						<th>Costo</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td style="font-weight:bold">Importe</td>
						<td style="font-weight: bold" class="costo">${tipoAgenda?.importe}</td>					
					</tr>
				</tfoot>
				
				<tbody>					
					<g:each var="agenda" in="${tipoAgenda?.agendas}">
						<tr>
							<td>${agenda?.fechacita?.format('dd/MM/yyyy')}</td>
							<td>${agenda?.horacita?.format('HH:mm')}</td>
							<td>${agenda?.terapias.join(',')}</td>
							<td class="costo">${agenda?.costo}</td>					
						</tr>
					</g:each>
				</tbody>
			</table>						
		</div>	
	</g:each>
	
	
</div>