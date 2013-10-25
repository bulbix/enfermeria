

<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName"	value="${message(code: 'default.home.label', default: 'Entrada')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
	
	<style>
		.ui-tabs .ui-tabs-nav li a {font-size:11pt}
	</style>
	
</head>
<body>

	<g:javascript src="comunes.js"/>
	<g:javascript src="hojaRegistroClinico.js" />
	<g:javascript src="signosVitales.js" />
	<g:javascript src="tablas.js" />
	<g:javascript src="controlLiquidosMedicamentos.js" />
	
	<div class="nav" role="navigation">
		<ul>
			<li><a href="${createLink(action: 'index')}">Nuevo</a></li>
			<li><a href="#" id="abrir" class="edit botonOperacion" onclick="mostrarHojas()">Abrir</a></li>	
			<li>
				<a href="#" 
				id="reporte" class="edit botonOperacion" 
				onclick="javascript:location.href='/enfermeria/hojaRegistroClinico/reporteHoja/'+document.getElementById('idHoja').value">PDF</a>
			</li>
			
			<g:if test="${hojaInstance?.id}">
				<li>
					<input type="button" onclick="mostrarFirma('0',true,'Jefe')" value="Firmar Jef@ ${hojaInstance.turnoActual}"/>				
				</li>
				<li>
					<input type="button" onclick="mostrarFirma('0',true,'Supervisor')" value="Firmar Supervis@r ${hojaInstance.turnoActual}"/>
				</li>
			</g:if>			
		</ul>
	</div>
	
	<div id="mostrarRegistros">
	</div>
	
	<div id="mostrarFirma">			
	</div>
	
	<form id="formHojaEnfermeria">	

	<div>
		<table>
			<tr>
				<td>
					<label for="pisos">Piso:</label>
					<g:select name="pisos" from="${pisos}"  noSelection="${['-1':'[T O D O S]']}" optionKey="id" optionValue="descripcion" />
				</td>
				<td>
					<label for="servicios">Servicio:</label>					
					<g:select name="servicios" from="" noSelection="${['-1':'[T O D O S]']}" optionKey="id" optionValue="descripcion"  />
				</td>
				<td>
					<label for="servicios">Cargar Historicos:</label>	
					<g:checkBox name="historico"  />
				</td>				
			</tr>
		</table>
		
		<input type="hidden" id="idHoja" name="idHoja" value="${hojaInstance?.id}"/>			
		
		<table>
			<tr>
				<td>
					<label for="pacienteauto">Paciente</label> 
					<g:textField name="pacienteauto" style="width: 500px;" value="${hojaInstance?.paciente}" />
					<input type="hidden" name="idPaciente" id="idPaciente" value="${hojaInstance?.paciente?.id}"/>					
				</td>
				<td>
					<label for="turno">Turno</label> 				
					<g:select name="turno" from="${['MATUTINO', 'VESPERTINO','NOCTURNO']}" 
							value="${hojaInstance?.turnoActual}"  />
				</td>
				<td>
					<label for="fechaElaboracion">Fecha</label> <g:textField
						name="fechaElaboracion"  value="${hojaInstance?.fechaElaboracion?.format('dd/MM/yyyy')}" size="8" />
				</td>
			</tr>
			
		</table>
	</div>

	<div>	
		<table>
			<tr>
				<td>
					<input type="hidden"name="idAdmision" id="idAdmision" 
					value="${hojaInstance?.admision?.id}"/>
					
					<label for="edad">Edad:</label>
					<label id="edad">${hojaInstance?.paciente?.fechanacimiento?.age}</label>
				</td>
				
				<td>
					<label for="sexo">Sexo:</label>
					<label id="sexo">${hojaInstance?.paciente?.sexo}</label>
				</td>
				
				<td>
					<label for="religion">Religion:</label>
					<label id="religion">${hojaInstance?.paciente?.datosPaciente?.toArray()?.getAt(0)?.religion}</label>
				</td>
				
				<td>
					<label for="cama">Cama:</label>
					<label id="cama">${hojaInstance?.admision?.cama?.numerocama}</label>
				</td>
								
				<td>
					<label for="diasHosp">Dias Hosp:</label>
					<label id="diasHosp">${hojaInstance?.admision?.diasHosp}</label>
				</td>				
			</tr>			
		</table>
		
		<table>
			<tr>
				<td>
					<label for="servicio">Servicio:</label>
					<label id="servicio">${hojaInstance?.admision?.servicio}</label>
				</td>
				
				<td>
					<label for="diagnostico">Diagnostico:</label>
					<label id="diagnostico">${hojaInstance?.admision?.diagnosticoIngreso}</label>
				</td>
			</tr>
		</table>
	
	</div>
	
	
	<div id="mensaje">${mensaje}</div>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Alergias y<br>Comorbilidad</a></li>
			<li><a href="#tabs-2">Signos<br>Vitales</a></li>
			<li><a href="#tabs-3">Control de Liquidos y<br>Medicamentos</a></li>
			<li><a href="#tabs-4">Valoración de<br>Enfermeria</a></li>
			<li><a href="#tabs-5">Diagnosticos e<br>Intervenciones</a></li>
			<li><a href="#tabs-6">Indicadores de<br>Calidad</a></li>
		</ul>
		<div id="tabs-1">
			<g:include action="alergiasComorbilidad" model="[hojaInstance:hojaInstance]"/>			
		</div>
		<div id="tabs-2">
			<g:include action="signosVitales" model="[hojaInstance:hojaInstance]"/>		
		</div>
		<div id="tabs-3">
			<g:include action="controlLiquidosMedicamentos"/>	
		</div>
		<div id="tabs-4">
			<g:include action="valoracionEnfermeria"/>		
		</div>
		<div id="tabs-5">
			<g:include action="diagnosticosIntervenciones"/>		
		</div>
		<div id="tabs-6">
			<g:include action="indicadoresCalidad"/>			
		</div>
	</div>
	
	</form>
	
	

</body>

</html>
