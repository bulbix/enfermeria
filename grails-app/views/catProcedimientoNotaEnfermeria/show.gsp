
<%@ page import="mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'catProcedimientoNotaEnfermeria.label', default: 'CatProcedimientoNotaEnfermeria')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-catProcedimientoNotaEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-catProcedimientoNotaEnfermeria" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list catProcedimientoNotaEnfermeria">
			
				<g:if test="${catProcedimientoNotaEnfermeriaInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="catProcedimientoNotaEnfermeria.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${catProcedimientoNotaEnfermeriaInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${catProcedimientoNotaEnfermeriaInstance?.estatus}">
				<li class="fieldcontain">
					<span id="estatus-label" class="property-label"><g:message code="catProcedimientoNotaEnfermeria.estatus.label" default="Estatus" /></span>
					
						<span class="property-value" aria-labelledby="estatus-label"><g:fieldValue bean="${catProcedimientoNotaEnfermeriaInstance}" field="estatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${catProcedimientoNotaEnfermeriaInstance?.padre}">
				<li class="fieldcontain">
					<span id="padre-label" class="property-label"><g:message code="catProcedimientoNotaEnfermeria.padre.label" default="Padre" /></span>
					
						<span class="property-value" aria-labelledby="padre-label"><g:link controller="catRubroNotaEnfermeria" action="show" id="${catProcedimientoNotaEnfermeriaInstance?.padre?.id}">${catProcedimientoNotaEnfermeriaInstance?.padre?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${catProcedimientoNotaEnfermeriaInstance?.vista}">
				<li class="fieldcontain">
					<span id="vista-label" class="property-label"><g:message code="catProcedimientoNotaEnfermeria.vista.label" default="Vista" /></span>
					
						<span class="property-value" aria-labelledby="vista-label"><g:formatBoolean boolean="${catProcedimientoNotaEnfermeriaInstance?.vista}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${catProcedimientoNotaEnfermeriaInstance?.id}" />
					<g:link class="edit" action="edit" id="${catProcedimientoNotaEnfermeriaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
