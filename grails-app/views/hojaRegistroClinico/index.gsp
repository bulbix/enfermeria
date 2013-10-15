

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
		
		<table>
			<tr>
				<td>
					<label for="pacienteauto">Paciente</label> 
					<g:textField name="pacienteauto" style="width: 500px;" />
					<input type="hidden"name="idPaciente" id="idPaciente"/>
				</td>
				<td>
					<label for="turno">Turno</label> 				
					<g:select name="turno" from="${['MATUTITNO', 'VESPERTINO','NOCTURNO']}" 
							value=""  />
				</td>
				<td>
					<label for="fecha">Fecha</label> <g:textField
						name="fecha" value=""
						size="9" />
				</td>
			</tr>
			
		</table>
	</div>

	<div>	
		<table>
			<tr>
				<td>
					<label for="edad">Edad:</label>
					<label id="edad" ></label>
				</td>
				
				<td>
					<label for="sexo">Sexo:</label>
					<label id="sexo"></label>
				</td>
				
				<td>
					<label for="religion">Religion:</label>
					<label id="religion"></label>
				</td>
				
				<td>
					<label for="cama">Cama:</label>
					<label id="cama"></label>
				</td>
								
				<td>
					<label for="diasHosp">Dias Hosp:</label>
					<label id="diasHosp"></label>
				</td>				
			</tr>			
		</table>
		
		<table>
			<tr>
				<td>
					<label for="servicio">Servicio:</label>
					<label id="servicio"></label>
				</td>
				
				<td>
					<label for="diagnostico">Diagnostico:</label>
					<label id="diagnostico"></label>
				</td>
			</tr>
		</table>
	
	</div>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Alergias y<br>Comorbilidad</a></li>
			<li><a href="#tabs-2">Signos<br>Vitales</a></li>
			<li><a href="#tabs-3">Control de Liquidos y<br>Medicamentos</a></li>
			<li><a href="#tabs-4">Valoración de Enfermeria</a></li>
			<li><a href="#tabs-5">Diagnosticos e<br>Intervenciones</a></li>
			<li><a href="#tabs-6">Indicadores de<br>Calidad</a></li>
		</ul>
		<div id="tabs-1">
			<g:include action="alergiasComorbilidad"/>			
		</div>
		<div id="tabs-2">
			<g:include action="signosVitales"/>		
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

</body>

</html>
