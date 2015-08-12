<%@ page import="mx.gob.inr.utils.Paciente" %>



<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'admisiones', 'error')} ">
	<label for="admisiones">
		<g:message code="paciente.admisiones.label" default="Admisiones" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${pacienteInstance?.admisiones?}" var="a">
    <li><g:link controller="admisionHospitalaria" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="admisionHospitalaria" action="create" params="['paciente.id': pacienteInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'admisionHospitalaria.label', default: 'AdmisionHospitalaria')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'datosPaciente', 'error')} ">
	<label for="datosPaciente">
		<g:message code="paciente.datosPaciente.label" default="Datos Paciente" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${pacienteInstance?.datosPaciente?}" var="d">
    <li><g:link controller="datosPaciente" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="datosPaciente" action="create" params="['paciente.id': pacienteInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'datosPaciente.label', default: 'DatosPaciente')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'fechanacimiento', 'error')} required">
	<label for="fechanacimiento">
		<g:message code="paciente.fechanacimiento.label" default="Fechanacimiento" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechanacimiento" precision="day"  value="${pacienteInstance?.fechanacimiento}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'idtipopaciente', 'error')} ">
	<label for="idtipopaciente">
		<g:message code="paciente.idtipopaciente.label" default="Idtipopaciente" />
		
	</label>
	<g:textField name="idtipopaciente" value="${pacienteInstance?.idtipopaciente}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'materno', 'error')} ">
	<label for="materno">
		<g:message code="paciente.materno.label" default="Materno" />
		
	</label>
	<g:textField name="materno" value="${pacienteInstance?.materno}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'nombre', 'error')} ">
	<label for="nombre">
		<g:message code="paciente.nombre.label" default="Nombre" />
		
	</label>
	<g:textField name="nombre" value="${pacienteInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'numeroregistro', 'error')} ">
	<label for="numeroregistro">
		<g:message code="paciente.numeroregistro.label" default="Numeroregistro" />
		
	</label>
	<g:textField name="numeroregistro" value="${pacienteInstance?.numeroregistro}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'paterno', 'error')} ">
	<label for="paterno">
		<g:message code="paciente.paterno.label" default="Paterno" />
		
	</label>
	<g:textField name="paterno" value="${pacienteInstance?.paterno}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pacienteInstance, field: 'sexo', 'error')} ">
	<label for="sexo">
		<g:message code="paciente.sexo.label" default="Sexo" />
		
	</label>
	<g:textField name="sexo" value="${pacienteInstance?.sexo}"/>
</div>

