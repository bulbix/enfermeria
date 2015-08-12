
<%@ page import="mx.gob.inr.catalogos.CatRubroNotaEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'catRubroNotaEnfermeria.label', default: 'CatRubroNotaEnfermeria')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-catRubroNotaEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-catRubroNotaEnfermeria" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="descripcion" title="${message(code: 'catRubroNotaEnfermeria.descripcion.label', default: 'Descripcion')}" />
					
						<th><g:message code="catRubroNotaEnfermeria.padre.label" default="Padre" /></th>
					
						<g:sortableColumn property="tipo" title="${message(code: 'catRubroNotaEnfermeria.tipo.label', default: 'Tipo')}" />
					
						<g:sortableColumn property="vista" title="${message(code: 'catRubroNotaEnfermeria.vista.label', default: 'Vista')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${catRubroNotaEnfermeriaInstanceList}" status="i" var="catRubroNotaEnfermeriaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${catRubroNotaEnfermeriaInstance.id}">${fieldValue(bean: catRubroNotaEnfermeriaInstance, field: "descripcion")}</g:link></td>
					
						<td>${fieldValue(bean: catRubroNotaEnfermeriaInstance, field: "padre")}</td>
					
						<td>${fieldValue(bean: catRubroNotaEnfermeriaInstance, field: "tipo")}</td>
					
						<td><g:formatBoolean boolean="${catRubroNotaEnfermeriaInstance.vista}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${catRubroNotaEnfermeriaInstanceTotal}" />
			</div>
			<export:formats />
		</div>
	</body>
</html>
