<%@ page import="mx.gob.inr.hojaRegistroClinico.HojaRegistroEnfermeria" %>



<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'admision', 'error')} required">
	<label for="admision">
		<g:message code="hojaRegistroEnfermeria.admision.label" default="Admision" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="admision" name="admision.id" from="${mx.gob.inr.utils.AdmisionHospitalaria.list()}" optionKey="id" required="" value="${hojaRegistroEnfermeriaInstance?.admision?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'alergias', 'error')} ">
	<label for="alergias">
		<g:message code="hojaRegistroEnfermeria.alergias.label" default="Alergias" />
		
	</label>
	<g:textField name="alergias" value="${hojaRegistroEnfermeriaInstance?.alergias}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'comorbilidad', 'error')} ">
	<label for="comorbilidad">
		<g:message code="hojaRegistroEnfermeria.comorbilidad.label" default="Comorbilidad" />
		
	</label>
	<g:textField name="comorbilidad" value="${hojaRegistroEnfermeriaInstance?.comorbilidad}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'fechaElaboracion', 'error')} required">
	<label for="fechaElaboracion">
		<g:message code="hojaRegistroEnfermeria.fechaElaboracion.label" default="Fecha Elaboracion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaElaboracion" precision="day"  value="${hojaRegistroEnfermeriaInstance?.fechaElaboracion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'otros', 'error')} ">
	<label for="otros">
		<g:message code="hojaRegistroEnfermeria.otros.label" default="Otros" />
		
	</label>
	<g:textField name="otros" value="${hojaRegistroEnfermeriaInstance?.otros}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'paciente', 'error')} required">
	<label for="paciente">
		<g:message code="hojaRegistroEnfermeria.paciente.label" default="Paciente" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="paciente" name="paciente.id" from="${mx.gob.inr.utils.Paciente.list()}" optionKey="id" required="" value="${hojaRegistroEnfermeriaInstance?.paciente?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'peso', 'error')} required">
	<label for="peso">
		<g:message code="hojaRegistroEnfermeria.peso.label" default="Peso" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="peso" value="${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: 'peso')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'talla', 'error')} required">
	<label for="talla">
		<g:message code="hojaRegistroEnfermeria.talla.label" default="Talla" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="talla" value="${fieldValue(bean: hojaRegistroEnfermeriaInstance, field: 'talla')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: hojaRegistroEnfermeriaInstance, field: 'turnos', 'error')} ">
	<label for="turnos">
		<g:message code="hojaRegistroEnfermeria.turnos.label" default="Turnos" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${hojaRegistroEnfermeriaInstance?.turnos?}" var="t">
    <li><g:link controller="hojaRegistroEnfermeriaTurno" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="hojaRegistroEnfermeriaTurno" action="create" params="['hojaRegistroEnfermeria.id': hojaRegistroEnfermeriaInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'hojaRegistroEnfermeriaTurno.label', default: 'HojaRegistroEnfermeriaTurno')])}</g:link>
</li>
</ul>

</div>

