<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName"	value="${message(code: 'default.home.label', default: 'Entrada')}" />
	<title>Hoja Registro Clinico de Enfermeria</title>
	
	<style>
		.ui-tabs .ui-tabs-nav li a {font-size:11pt}
	</style>
	
</head>
<body>

	<g:javascript src="validaciones.js"/>
	<g:javascript src="comunes.js"/>
	<g:javascript src="hojaRegistroClinico.js" />
	<g:javascript src="signosVitales.js" />
	<g:javascript src="tablas.js" />
	<g:javascript src="controlLiquidosMedicamentos.js" />
	<g:javascript src="indicadoresCalidad.js" />
	
	
	
	
	<div class="nav" role="navigation">
		<ul>
			<li><a href="${createLink(action: 'index')}" class="nuevo">Nuevo</a></li>
			<li><a style="display:none" id="abrir"class="aceptar" onclick="mostrarHojas()" class="aceptar">Abrir</a></li>		
			
			<g:if test="${hojaInstance?.id}">
			
				<li>
					<a style="cursor:pointer" id="reporte" class="imprimir" onclick="imprimirHoja()">PDF</a>
				</li>
			
				<g:existeFirma idHoja="${hojaInstance.id}" turno="${hojaInstance.turnoActual}" tipoUsuario="Jefe">
					<li>
					<a class="aceptar" onclick="mostrarFirma('0',true,'Jefe')" >Firmar Jef@ ${hojaInstance.turnoActual}</a>				
					</li>
				</g:existeFirma>
				<g:existeFirma idHoja="${hojaInstance.id}" turno="${hojaInstance.turnoActual}" tipoUsuario="Supervisor">
					<li>
						<a class="aceptar" onclick="mostrarFirma('0',true,'Supervisor')">Firmar Supervis@r ${hojaInstance.turnoActual}</a>
					</li>
				</g:existeFirma>				
			</g:if>			
		</ul>
	</div>	
	
	<div id="mostrarHojas">			
	</div>
	
	<div id="mostrarFirma">			
	</div>
	
	<div id="mostrarRegistros">
	</div>
	
	<div id="dialog-confirm">  		
	
	</div>
	
	<div id="dialog-mensaje">  		
	
	</div>
	
	
	<%--Rdireccionar con post --%>
	<form id="formRedirect" style="display: hidden" action="/enfermeria/hojaRegistroClinico/consultarHoja" method="POST">
  		<input type="hidden" id="idHojaR" name="idHoja" value=""/>
  		<input type="hidden" id="turnoActualR" name="turnoActual" value=""/>
  		<input type="hidden" id="mensajeR" name="mensaje" value=""/>
  		<input type="hidden" id="nuevaHojaR" name="nuevaHoja" value=""/>
	</form>
	
	
	
	<form id="formHojaEnfermeria">	

	<div >
		<table id="tablaFiltro">
			<tr>
				<td>
					<label for="pisos">Piso:</label>
					<g:select name="pisos" from="${pisos}"  noSelection="${['-1':'[T O D O S]']}" optionKey="id" optionValue="descripcion" class="cabecera" />
				</td>
				<td>
					<label for="servicios">Servicio:</label>					
					<g:select name="servicios" from="" noSelection="${['-1':'[T O D O S]']}" optionKey="id" optionValue="descripcion" class="cabecera"  />
				</td>
				<td>
					<label for="servicios">Cargar Historicos:</label>	
					<g:checkBox name="historico" class="cabecera"  />
				</td>				
			</tr>
		</table>
		
		<input type="hidden" id="idHoja" name="idHoja" value="${hojaInstance?.id}"/>
		<input type="hidden" id="idUsuarioActual" name="idUsuarioActual" value="${usuarioActual?.id}"/>
		<input type="hidden" id="soloLectura" name="soloLectura" value="${soloLectura}"/>
		<input type="hidden" id="nombrePaciente" name="nombrePaciente" value="${hojaInstance?.paciente?.nombreCompleto}"/>
		
		<table id="tablaCaptura">
			<tr>
				<td>
					<label for="pacienteauto">Paciente:</label> 
					<g:textField name="pacienteauto" style="width: 500px;" value="${hojaInstance?.paciente}" class="cabecera" />
					<input type="hidden" name="idPaciente" id="idPaciente" value="${hojaInstance?.paciente?.id}" />					
				</td>
				<td>
					<label for="turno">Turno:</label> 				
					<g:select name="turno" id="turno" from="${['MATUTINO', 'VESPERTINO','NOCTURNO']}" 
							value="${hojaInstance?.turnoActual}"  class="cabecera" />
				</td>
				<td>
					<label for="fechaElaboracion">Fecha:</label>
					<g:textField name="fechaElaboracion"  
					value="${hojaInstance?.fechaElaboracion?.format('dd/MM/yyyy')}" 
					size="8" class="cabecera" readonly="true" />
				</td>
			</tr>			
		</table>
		
		<table id="tablaLectura" style="display:none">
			<tr>
				<td>
					<label>Paciente:</label> 
					<label class="cabecera">${hojaInstance?.paciente}</label>
										
				</td>
				<td>
					<label>Turno:</label> 
					<label class="cabecera">${hojaInstance?.turnoActual}</label>
				</td>
				<td>
					<label>Fecha:</label>					
					<label class="cabecera">${hojaInstance?.fechaElaboracion?.format('dd/MM/yyyy')}</label>
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
					<label id="edad" class="cabecera">${hojaInstance?.paciente?.fechanacimiento?.age}</label>
				</td>
				
				<td>
					<label for="sexo">Sexo:</label>
					<label id="sexo" class="cabecera">${hojaInstance?.paciente?.sexo}</label>
				</td>
				
				<td>
					<label for="religion">Religion:</label>
					<label id="religion" class="cabecera">${hojaInstance?.paciente?.datosPaciente?.toArray()?.getAt(0)?.religion}</label>
				</td>
				
				<td>
					<label for="cama">Cama:</label>
					<label id="cama" class="cabecera">${hojaInstance?.admision?.cama?.numerocama}</label>
				</td>
								
				<td>
					<label for="diasHosp">Dias Hosp:</label>
					<label id="diasHosp" class="cabecera">${hojaInstance?.admision?.diasHosp}</label>
				</td>				
			</tr>			
		</table>
		
		<table>
			<tr>
				<td>
					<label for="servicio">Servicio:</label>
					<label id="servicio" class="cabecera">${hojaInstance?.admision?.servicio}</label>
				</td>
				
				<td>
					<label for="diagnostico">Diagnostico:</label>
					<label id="diagnostico" class="cabecera">${hojaInstance?.admision?.diagnosticoIngreso}</label>
				</td>
			</tr>
		</table>
	
	</div>
	

	
	
	<div class="mensaje" id="mensaje" style="color:red;font-size:20px">${mensaje}</div>

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
			<g:include action="signosVitales" model="[hojaInstance:hojaInstance,usuarioActual:usuarioActual]"/>		
		</div>
		<div id="tabs-3">
			<g:include action="controlLiquidosMedicamentos" model="[hojaInstance:hojaInstance]"/>	
		</div>
		<div id="tabs-4">
			<g:include action="valoracionEnfermeria" model="[hojaInstance:hojaInstance]"/>		
		</div>
		<div id="tabs-5">
			<g:include action="diagnosticosIntervenciones" model="[hojaInstance:hojaInstance]"/>		
		</div>
		<div id="tabs-6">
			<g:include action="indicadoresCalidad" model="[hojaInstance:hojaInstance]"/>			
		</div>
	</div>
	
	</form>
	
	

</body>

</html>
