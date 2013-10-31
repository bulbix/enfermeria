
<div style="position:fixed;background-color:rgb(190,214,248);top:0;left:0">
	<div class="nav" role="navigation">
			<ul>
				<li>
					<a class="guardar"><g:submitToRemote value="Guardar" controller="signosVitales" action="guardarSignosVitales" update="mensaje" /></a>
				</li>
				<li>
					<a href="#arribaHoja" class="arriba">IR ARRIBA</a>
				</li>
				<li>
					<a href="#abajoSigno" class="abajo">IR ABAJO</a>
				</li>
			</ul>
	</div>
</div>



<input type="button" id="addSignosVitales" value="SIGNOS VITALES"/>
<div style="height:300px;overflow:auto;" >
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
					<td><g:textField name="hora" value="${signo.hora}" size="5"  onkeypress="return isNumberKey(event)"/></td>			
					<td><g:textField name="temperatura" value="${signo.temperatura?.otro}" size="5" onkeypress="return isNumberPointKey(event)"/></td>			
					<td><g:textField name="cardiaca" value="${signo.cardiaca?.otro}" size="5" onkeypress="return isNumberKey(event)"/></td>			
					<td><g:textField name="sistolica" value="${signo.sistolica?.otro}" size="5" onkeypress="return isNumberKey(event)"/></td>			
					<td><g:textField name="diastolica" value="${signo.diastolica?.otro}" size="5" onkeypress="return isNumberKey(event)"/></td>			
					<td><g:textField name="respiracion" value="${signo.respiracion?.otro}" size="5" onkeypress="return isNumberKey(event)"/></td>			
					<td><g:textField name="gabinete" value="${signo.gabinete?.otro}" size="5"/></td>		
				</tr>
			</g:each>		
		</tbody>
	</table>
</div>

<input type="button" id="showEscalaDolor" value="ESCALA DEL DOLOR" onclick="mostrarEscalaDolor()"/>
<label for="horaDolor">Hora:</label> <g:textField name="horaDolor" class="hora" size="5" value="1"/>

<div>
	<table>	
		<tr>	
			<g:each in="${(0..10)}" var="i">				
				<td>
					<a class="escalaDolorImagen" style="cursor:pointer" onclick="guardarEscalaDolor(${i})" title="Valor ${i}"> 
						<img  src="${createLinkTo(dir: 'images/escaladolor', file:"Dolor${i % 2 == 0 ? i : 'Otro' }.png")}" alt="Dolor${i}" />
					</a>				
				</td>
			</g:each>
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
				<td><g:textArea name="dieta" rows="5" cols="16" value="${hojaInstance.dietas?.getAt(0)?.otro}"/></td>			
				<td>
				<label>Hora:</label>
				<g:textField name="horaDieta" class="hora" size="5" value="${hojaInstance.dietas?.getAt(1)?.horaregistrodiagva?:'8'}"/>
				<g:textArea name="dieta" id="dietaMatutino" rows="5" cols="16" value="${hojaInstance.dietas?.getAt(1)?.otro}"/></td>			
				<td>
				<label>Hora:</label>
				<g:textField name="horaDieta"  class="hora" size="5" value="${hojaInstance.dietas?.getAt(2)?.horaregistrodiagva?:'15'}"/>
				<g:textArea name="dieta" id="dietaVespertino" rows="5" cols="16" value="${hojaInstance.dietas?.getAt(2)?.otro}"/></td>			
				<td>
				<label>Hora:</label>
				<g:textField name="horaDieta"  class="hora" size="5" value="${hojaInstance.dietas?.getAt(3)?.horaregistrodiagva?:'1'}"/>
				<g:textArea name="dieta" id="dietaNocturno" rows="5" cols="16" value="${hojaInstance.dietas?.getAt(3)?.otro}"/></td>		
			</tr>
		</tbody>
	</table>
</div>


<a name="abajoSigno"></a>