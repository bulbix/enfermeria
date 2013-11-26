<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName"	value="${message(code: 'default.home.label', default: 'Entrada')}" />
	<title>Seguimiento Hospitalario</title>
	
	<style>
		.ui-tabs .ui-tabs-nav li a {font-size:11pt}
	</style>
	
</head>
<body>

	<g:javascript src="seguimientoHosp/seguimientoHosp.js" />
	<g:javascript src="seguimientoHosp/medicamento.js" />
	
	<div class="nav" role="navigation">
		<ul>
			<li><a href="${createLink(action: 'index')}" class="nuevo">Nuevo</a></li>
			<li><a style="display:none" id="abrir"class="aceptar" onclick="mostrarSeguimientos()" class="aceptar">Abrir</a></li>						
		</ul>
	</div>
	
	<div id="mostrarSeguimientos">			
	</div>
	
	<div id="dialog-confirm">
	</div>
	
	<div id="dialog-mensaje"> 
	</div>
	
	<%--Rdireccionar con post --%>
	<form id="formRedirect" style="display: hidden" action="/enfermeria/seguimientoHosp/consultarSeguimiento" method="POST">
  		<input type="hidden" id="idSeguimientoR" name="idSeguimiento" value=""/>
  		<input type="hidden" id="mensajeR" name="mensaje" value=""/>
	</form>
	
	<form id="formSeguimientoHosp">	
	
		<input type="hidden" id="tipoHistorico" name="tipoHistorico" value="seguimiento"/>
		<input type="hidden" id="idSeguimiento" name="idSeguimiento" value="${seguimientoHosp?.id}"/>
		<input type="hidden" id="idUsuarioActual" name="idUsuarioActual" value="${usuarioActual?.id}"/>
		<input type="hidden" id="soloLectura" name="soloLectura" value="${soloLectura}"/>
		<input type="hidden" id="nombrePaciente" name="nombrePaciente" value="${seguimientoHosp?.paciente?.nombreCompleto}"/>
	
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
			
			<table id="tablaCaptura">
				<tr>
					<td>
						<label for="pacienteauto">Paciente:</label> 
						<g:textField name="pacienteauto" style="width: 500px;" value="${seguimientoHosp?.paciente}" class="cabecera" title="Busque por nombre, registro o cama" />
						<input type="hidden" name="idPaciente" id="idPaciente" value="${seguimientoHosp?.paciente?.id}" />					
					</td>
					<%-- <td>
						<label for="turno">Turno:</label> 				
						<g:select name="turno" id="turno" from="${['MATUTINO', 'VESPERTINO','NOCTURNO']}" 
								value="${seguimientoHosp?.turnoActual}"  class="cabecera" />
					</td>
					--%>
					<td>
						<label for="fechaElaboracion">Fecha:</label>
						<g:textField name="fechaElaboracion"  
						value="${seguimientoHosp?.fechaElaboracion?.format('dd/MM/yyyy')}" 
						size="8" class="cabecera" readonly="true" />
					</td>
				</tr>			
			</table>
			
			<table id="tablaLectura" style="display:none">
				<tr>
					<td>
						<label>Paciente:</label> 
						<label class="cabecera">${seguimientoHosp?.paciente}</label>
											
					</td>
					<%-- 
					<td>
						<label>Turno:</label> 
						<label class="cabecera">${seguimientoHosp?.turnoActual}</label>
					</td>
					--%>
					<td>
						<label>Fecha:</label>					
						<label class="cabecera">${seguimientoHosp?.fechaElaboracion?.format('dd/MM/yyyy')}</label>
					</td>
				</tr>			
			</table>
		</div>

		<div>	
			<table>
				<tr>
					<td>
						<input type="hidden"name="idAdmision" id="idAdmision" 
						value="${seguimientoHosp?.admision?.id}"/>
						
						<label for="edad">Edad:</label>
						<label id="edad" class="cabecera">${seguimientoHosp?.paciente?.fechanacimiento?.age}</label>
					</td>
					
					<td>
						<label for="sexo">Sexo:</label>
						<label id="sexo" class="cabecera">${seguimientoHosp?.paciente?.sexo}</label>
					</td>
					
					<td>
						<label for="religion">Religion:</label>
						<label id="religion" class="cabecera">${seguimientoHosp?.paciente?.datosPaciente?.toArray()?.getAt(0)?.religion}</label>
					</td>
					
					<td>
						<label for="cama">Cama:</label>
						<label id="cama" class="cabecera">${seguimientoHosp?.admision?.cama?.numerocama}</label>
					</td>
									
					<td>
						<label for="diasHosp">Dias Hosp:</label>
						<label id="diasHosp" class="cabecera">${seguimientoHosp?.admision?.diasHosp}</label>
					</td>				
				</tr>			
			</table>
			
			<table>
				<tr>
					<td>
						<label for="servicio">Servicio:</label>
						<label id="servicio" class="cabecera">${seguimientoHosp?.admision?.servicio}</label>
					</td>
					
					<td>
						<label for="diagnostico">Diagnostico:</label>
						<label id="diagnostico" class="cabecera">${seguimientoHosp?.admision?.diagnosticoIngreso}</label>
					</td>
				</tr>
			</table>
		
		</div>
		
	
	
		<div class="mensaje" id="mensaje" title="Mensaje" style="font-size:20px">${mensaje}</div>
		
		<div id="tabs">
				<ul>
					<li><a href="#tabs-1">Medicamentos</a></li>
					<li><a href="#tabs-2">Estudios</a></li>
					<li><a href="#tabs-3">Cirugias</a></li>
					<li><a href="#tabs-4">Terapias</a></li>				
				</ul>
				<div id="tabs-1">
					<g:include action="medicamento" model=""/>			
				</div>
				<div id="tabs-2">
					<g:include action="estudio" model=""/>		
				</div>
				<div id="tabs-3">
					<g:include action="cirugia" model=""/>	
				</div>
				<div id="tabs-4">
					<g:include action="terapia" model=""/>		
				</div>			
		</div>	
	
	</form>
		
	

</body>

</html>
