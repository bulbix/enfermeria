
<%@ page import="mx.gob.inr.utils.Paciente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'paciente.label', default: 'Paciente')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-paciente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-paciente" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list paciente">
			
				<g:if test="${pacienteInstance?.admisiones}">
				<li class="fieldcontain">
					<span id="admisiones-label" class="property-label"><g:message code="paciente.admisiones.label" default="Admisiones" /></span>
					
						<g:each in="${pacienteInstance.admisiones}" var="a">
						<span class="property-value" aria-labelledby="admisiones-label"><g:link controller="admisionHospitalaria" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.datosPaciente}">
				<li class="fieldcontain">
					<span id="datosPaciente-label" class="property-label"><g:message code="paciente.datosPaciente.label" default="Datos Paciente" /></span>
					
						<g:each in="${pacienteInstance.datosPaciente}" var="d">
						<span class="property-value" aria-labelledby="datosPaciente-label"><g:link controller="datosPaciente" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.fechanacimiento}">
				<li class="fieldcontain">
					<span id="fechanacimiento-label" class="property-label"><g:message code="paciente.fechanacimiento.label" default="Fechanacimiento" /></span>
					
						<span class="property-value" aria-labelledby="fechanacimiento-label"><g:formatDate date="${pacienteInstance?.fechanacimiento}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.idtipopaciente}">
				<li class="fieldcontain">
					<span id="idtipopaciente-label" class="property-label"><g:message code="paciente.idtipopaciente.label" default="Idtipopaciente" /></span>
					
						<span class="property-value" aria-labelledby="idtipopaciente-label"><g:fieldValue bean="${pacienteInstance}" field="idtipopaciente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.materno}">
				<li class="fieldcontain">
					<span id="materno-label" class="property-label"><g:message code="paciente.materno.label" default="Materno" /></span>
					
						<span class="property-value" aria-labelledby="materno-label"><g:fieldValue bean="${pacienteInstance}" field="materno"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="paciente.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${pacienteInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.numeroregistro}">
				<li class="fieldcontain">
					<span id="numeroregistro-label" class="property-label"><g:message code="paciente.numeroregistro.label" default="Numeroregistro" /></span>
					
						<span class="property-value" aria-labelledby="numeroregistro-label"><g:fieldValue bean="${pacienteInstance}" field="numeroregistro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.paterno}">
				<li class="fieldcontain">
					<span id="paterno-label" class="property-label"><g:message code="paciente.paterno.label" default="Paterno" /></span>
					
						<span class="property-value" aria-labelledby="paterno-label"><g:fieldValue bean="${pacienteInstance}" field="paterno"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pacienteInstance?.sexo}">
				<li class="fieldcontain">
					<span id="sexo-label" class="property-label"><g:message code="paciente.sexo.label" default="Sexo" /></span>
					
						<span class="property-value" aria-labelledby="sexo-label"><g:fieldValue bean="${pacienteInstance}" field="sexo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pacienteInstance?.id}" />
					<g:link class="edit" action="edit" id="${pacienteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
