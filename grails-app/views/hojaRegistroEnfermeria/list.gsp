
<%@ page import="mx.gob.inr.hojaRegistroClinico.HojaRegistroEnfermeria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hojaRegistroEnfermeria.label', default: 'HojaRegistroEnfermeria')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-hojaRegistroEnfermeria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-hojaRegistroEnfermeria" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="hojaRegistroEnfermeria.admision.label" default="Admision" /></th>
					
						<g:sortableColumn property="alergias" title="${message(code: 'hojaRegistroEnfermeria.alergias.label', default: 'Alergias')}" />
					
						<g:sortableColumn property="comorbilidad" title="${message(code: 'hojaRegistroEnfermeria.comorbilidad.label', default: 'Comorbilidad')}" />
					
						<g:sortableColumn property="fechaElaboracion" title="${message(code: 'hojaRegistroEnfermeria.fechaElaboracion.label', default: 'Fecha Elaboracion')}" />
					
						<g:sortableColumn property="otros" title="${message(code: 'hojaRegistroEnfermeria.otros.label', default: 'Otros')}" />
					
						<th><g:message code="hojaRegistroEnfermeria.paciente.label" default="Paciente" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hojaRegistroEnfermeriaInstanceList}" status="i" var="hojaRegistroEnfermeriaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${hojaRegistroEnfermeriaInstance.id}">${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: "admision")}</g:link></td>
					
						<td>${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: "alergias")}</td>
					
						<td>${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: "comorbilidad")}</td>
					
						<td><g:formatDate date="${hojaRegistroEnfermeriaInstance.fechaElaboracion}" /></td>
					
						<td>${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: "otros")}</td>
					
						<td>${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: "paciente")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hojaRegistroEnfermeriaInstanceTotal}" />
			</div>
			<export:formats />
		</div>
	</body>
</html>
