
<%@ page import="mx.gob.inr.utils.Paciente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'paciente.label', default: 'Paciente')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-paciente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-paciente" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="fechanacimiento" title="${message(code: 'paciente.fechanacimiento.label', default: 'Fechanacimiento')}" />
					
						<g:sortableColumn property="idtipopaciente" title="${message(code: 'paciente.idtipopaciente.label', default: 'Idtipopaciente')}" />
					
						<g:sortableColumn property="materno" title="${message(code: 'paciente.materno.label', default: 'Materno')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'paciente.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="numeroregistro" title="${message(code: 'paciente.numeroregistro.label', default: 'Numeroregistro')}" />
					
						<g:sortableColumn property="paterno" title="${message(code: 'paciente.paterno.label', default: 'Paterno')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pacienteInstanceList}" status="i" var="pacienteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pacienteInstance.id}">${fieldValue(bean: pacienteInstance, field: "fechanacimiento")}</g:link></td>
					
						<td>${fieldValue(bean: pacienteInstance, field: "idtipopaciente")}</td>
					
						<td>${fieldValue(bean: pacienteInstance, field: "materno")}</td>
					
						<td>${fieldValue(bean: pacienteInstance, field: "nombre")}</td>
					
						<td>${fieldValue(bean: pacienteInstance, field: "numeroregistro")}</td>
					
						<td>${fieldValue(bean: pacienteInstance, field: "paterno")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pacienteInstanceTotal}" />
			</div>
			<export:formats />
		</div>
	</body>
</html>
