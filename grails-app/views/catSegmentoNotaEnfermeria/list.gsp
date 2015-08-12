
<%@ page import="mx.gob.inr.catalogos.CatSegmentoNotaEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'catSegmentoNotaEnfermeria.label', default: 'CatSegmentoNotaEnfermeria')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-catSegmentoNotaEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-catSegmentoNotaEnfermeria" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="descripcion" title="${message(code: 'catSegmentoNotaEnfermeria.descripcion.label', default: 'Descripcion')}" />
					
						<th><g:message code="catSegmentoNotaEnfermeria.padre.label" default="Padre" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${catSegmentoNotaEnfermeriaInstanceList}" status="i" var="catSegmentoNotaEnfermeriaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${catSegmentoNotaEnfermeriaInstance.id}">${fieldValue(bean: catSegmentoNotaEnfermeriaInstance, field: "descripcion")}</g:link></td>
					
						<td>${fieldValue(bean: catSegmentoNotaEnfermeriaInstance, field: "padre")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${catSegmentoNotaEnfermeriaInstanceTotal}" />
			</div>
			<export:formats />
		</div>
	</body>
</html>
