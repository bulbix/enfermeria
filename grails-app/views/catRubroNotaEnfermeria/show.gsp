
<%@ page import="mx.gob.inr.catalogos.CatRubroNotaEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-catRubroNotaEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-catRubroNotaEnfermeria" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list catRubroNotaEnfermeria">
			
				<g:if test="${catRubroNotaEnfermeriaInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="catRubroNotaEnfermeria.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${catRubroNotaEnfermeriaInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${catRubroNotaEnfermeriaInstance?.padre}">
				<li class="fieldcontain">
					<span id="padre-label" class="property-label"><g:message code="catRubroNotaEnfermeria.padre.label" default="Padre" /></span>
					
						<span class="property-value" aria-labelledby="padre-label"><g:link controller="catSegmentoNotaEnfermeria" action="show" id="${catRubroNotaEnfermeriaInstance?.padre?.id}">${catRubroNotaEnfermeriaInstance?.padre?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${catRubroNotaEnfermeriaInstance?.tipo}">
				<li class="fieldcontain">
					<span id="tipo-label" class="property-label"><g:message code="catRubroNotaEnfermeria.tipo.label" default="Tipo" /></span>
					
						<span class="property-value" aria-labelledby="tipo-label"><g:fieldValue bean="${catRubroNotaEnfermeriaInstance}" field="tipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${catRubroNotaEnfermeriaInstance?.vista}">
				<li class="fieldcontain">
					<span id="vista-label" class="property-label"><g:message code="catRubroNotaEnfermeria.vista.label" default="Vista" /></span>
					
						<span class="property-value" aria-labelledby="vista-label"><g:formatBoolean boolean="${catRubroNotaEnfermeriaInstance?.vista}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${catRubroNotaEnfermeriaInstance?.id}" />
					<g:link class="edit" action="edit" id="${catRubroNotaEnfermeriaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
