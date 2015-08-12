
<%@ page import="mx.gob.inr.hojaRegistroClinico.HojaRegistroEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hojaRegistroEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-hojaRegistroEnfermeria" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hojaRegistroEnfermeria">
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.admision}">
				<li class="fieldcontain">
					<span id="admision-label" class="property-label"><g:message code="hojaRegistroEnfermeria.admision.label" default="Admision" /></span>
					
						<span class="property-value" aria-labelledby="admision-label"><g:link controller="admisionHospitalaria" action="show" id="${hojaRegistroEnfermeriaInstance?.admision?.id}">${hojaRegistroEnfermeriaInstance?.admision?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.alergias}">
				<li class="fieldcontain">
					<span id="alergias-label" class="property-label"><g:message code="hojaRegistroEnfermeria.alergias.label" default="Alergias" /></span>
					
						<span class="property-value" aria-labelledby="alergias-label"><g:fieldValue bean="${hojaRegistroEnfermeriaInstance}" field="alergias"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.comorbilidad}">
				<li class="fieldcontain">
					<span id="comorbilidad-label" class="property-label"><g:message code="hojaRegistroEnfermeria.comorbilidad.label" default="Comorbilidad" /></span>
					
						<span class="property-value" aria-labelledby="comorbilidad-label"><g:fieldValue bean="${hojaRegistroEnfermeriaInstance}" field="comorbilidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.fechaElaboracion}">
				<li class="fieldcontain">
					<span id="fechaElaboracion-label" class="property-label"><g:message code="hojaRegistroEnfermeria.fechaElaboracion.label" default="Fecha Elaboracion" /></span>
					
						<span class="property-value" aria-labelledby="fechaElaboracion-label"><g:formatDate date="${hojaRegistroEnfermeriaInstance?.fechaElaboracion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.otros}">
				<li class="fieldcontain">
					<span id="otros-label" class="property-label"><g:message code="hojaRegistroEnfermeria.otros.label" default="Otros" /></span>
					
						<span class="property-value" aria-labelledby="otros-label"><g:fieldValue bean="${hojaRegistroEnfermeriaInstance}" field="otros"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.paciente}">
				<li class="fieldcontain">
					<span id="paciente-label" class="property-label"><g:message code="hojaRegistroEnfermeria.paciente.label" default="Paciente" /></span>
					
						<span class="property-value" aria-labelledby="paciente-label"><g:link controller="paciente" action="show" id="${hojaRegistroEnfermeriaInstance?.paciente?.id}">${hojaRegistroEnfermeriaInstance?.paciente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.peso}">
				<li class="fieldcontain">
					<span id="peso-label" class="property-label"><g:message code="hojaRegistroEnfermeria.peso.label" default="Peso" /></span>
					
						<span class="property-value" aria-labelledby="peso-label"><g:fieldValue bean="${hojaRegistroEnfermeriaInstance}" field="peso"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.talla}">
				<li class="fieldcontain">
					<span id="talla-label" class="property-label"><g:message code="hojaRegistroEnfermeria.talla.label" default="Talla" /></span>
					
						<span class="property-value" aria-labelledby="talla-label"><g:fieldValue bean="${hojaRegistroEnfermeriaInstance}" field="talla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hojaRegistroEnfermeriaInstance?.turnos}">
				<li class="fieldcontain">
					<span id="turnos-label" class="property-label"><g:message code="hojaRegistroEnfermeria.turnos.label" default="Turnos" /></span>
					
						<g:each in="${hojaRegistroEnfermeriaInstance.turnos}" var="t">
						<span class="property-value" aria-labelledby="turnos-label"><g:link controller="hojaRegistroEnfermeriaTurno" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hojaRegistroEnfermeriaInstance?.id}" />
					<g:link class="edit" action="edit" id="${hojaRegistroEnfermeriaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
