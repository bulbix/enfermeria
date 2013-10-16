<input type="button" id="addSignosVitales" value="SIGNOS VITALES"/>

<div style="height:200px;overflow:auto;" >
	<table id="tablaSignosVitales">
		<thead>
			<tr>
				<th>Hora</th>
				<th>Temp.</th>
				<th>F.C.</th>
				<th>T.A. Sistolica</th>
				<th>T.A. Diastolica</th>
				<th>Frec Resp</th>
				<th>Lab y Gab</th>
			</tr>		
		</thead>	
		<tbody>
		<tr>
			<td>				
				<g:textField name="hora" size="5"/>
			</td>			
			<td>				
				<g:textField name="temperatura" size="5"/>
			</td>			
			<td>				
				<g:textField name="cardiaca" size="5"/>
			</td>			
			<td>			
				<g:textField name="sistolica" size="5"/>
			</td>			
			<td>				
				<g:textField name="diastolica" size="5"/>
			</td>			
			<td>				
				<g:textField name="respiracion" size="5"/>
			</td>			
			<td>				
				<g:textField name="gabinete" size="5"/>
			</td>		
		</tr>
		</tbody>
	</table>
</div>

<input type="button" id="showEscalaDolor" value="ESCALA DEL DOLOR"/>

<div>
	<table>	
	<tr>
	
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor0.png')}" alt="Dolor0"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor1"/></td>				
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor2.png')}" alt="Dolor2"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor3"/></td>				
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor4.png')}" alt="Dolor4"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor5"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor6.png')}" alt="Dolor6"/></td>	
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor7"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor8.png')}" alt="Dolor8"/></td>	
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor9"/></td>
	<td><img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor10.png')}" alt="Dolor10"/></td>
	</tr>
	</table>			

</div>

<div>
	<table id="tablaDietas">
		<thead>
			<tr>
				<th>GENERALIZADA</th>
				<th>MATUTINA</th>
				<th>VESPERTINA</th>			
				<th>NOCTURNA</th>
			</tr>		
		</thead>	
		<tbody>
		<tr>
			<td>				
				<g:textArea name="dietaGeneralizada" value="" rows="5" cols="18"/>
			</td>			
			<td>				
				<g:textArea name="dietaMatutina" value="" rows="5" cols="18"/>
			</td>			
			<td>				
				<g:textArea name="dietaVespertina" value="" rows="5" cols="18"/>
			</td>			
			<td>			
				<g:textArea name="dietaNocturna" value="" rows="5" cols="18"/>
			</td>		
		</tr>
		</tbody>
	</table>
</div>