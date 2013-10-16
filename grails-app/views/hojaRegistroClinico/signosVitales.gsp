<g:actionSubmit value="Guardar" action="guardarSignosVitales"/>

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
			<g:each in="${hojaInstance.signosVitales}" var="signo">		
				<tr>
					<td><g:textField name="hora" value="${signo.hora}" size="5"/></td>			
					<td><g:textField name="temperatura" value="${signo.temperatura?.otro}" size="5"/></td>			
					<td><g:textField name="cardiaca" value="${signo.cardiaca?.otro}" size="5"/></td>			
					<td><g:textField name="sistolica" value="${signo.sistolica?.otro}" size="5"/></td>			
					<td><g:textField name="diastolica" value="${signo.diastolica?.otro}" size="5"/></td>			
					<td><g:textField name="respiracion" value="${signo.respiracion?.otro}" size="5"/></td>			
					<td><g:textField name="gabinete" value="${signo.gabinete?.otro}" size="5"/></td>		
				</tr>
			</g:each>		
		</tbody>
	</table>
</div>

<input type="button" id="showEscalaDolor" value="ESCALA DEL DOLOR"/>

<br>
<g:each in="${hojaInstance.escalaDolor}" var="dolor">
	${dolor}<br>
</g:each>

<div>
	<table>	
		<tr>
			
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=0&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor0.png')}" alt="Dolor0" /></a></td>			
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=1&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor1"/></a></td>				
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=2&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor2.png')}" alt="Dolor2"/></a></td>
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=3&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor3"/></a></td>				
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=4&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor4.png')}" alt="Dolor4"/></a></td>
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=5&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor5"/></a></td>
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=6&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor6.png')}" alt="Dolor6"/></a></td>	
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=7&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor7"/></a></td>
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=8&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor8.png')}" alt="Dolor8"/></a></td>	
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=9&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'DolorOtro.png')}" alt="Dolor9"/></a></td>
			<td><a href="${createLink(action: 'guardarEscalaDolor')}?dolor=10&idHoja=${hojaInstance?.id}">
			<img src="${createLinkTo(dir: 'images/escaladolor', file:'Dolor10.png')}" alt="Dolor10"/></a></td>
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
				<td><g:textArea name="dietaGeneralizada" value="" rows="5" cols="18"/></td>			
				<td><g:textArea name="dietaMatutina" value="" rows="5" cols="18"/></td>			
				<td><g:textArea name="dietaVespertina" value="" rows="5" cols="18"/></td>			
				<td><g:textArea name="dietaNocturna" value="" rows="5" cols="18"/></td>		
			</tr>
		</tbody>
	</table>
</div>