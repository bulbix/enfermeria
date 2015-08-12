<%@ page import="mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria" %>



<div class="fieldcontain ${hasErrors(bean: catProcedimientoNotaEnfermeriaInstance, field: 'descripcion', 'error')} ">
	<label for="descripcion">
		<g:message code="catProcedimientoNotaEnfermeria.descripcion.label" default="Descripcion" />
		
	</label>
	<g:textField name="descripcion" value="${catProcedimientoNotaEnfermeriaInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catProcedimientoNotaEnfermeriaInstance, field: 'estatus', 'error')} ">
	<label for="estatus">
		<g:message code="catProcedimientoNotaEnfermeria.estatus.label" default="Estatus" />
		
	</label>
	<g:textField name="estatus" value="${catProcedimientoNotaEnfermeriaInstance?.estatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catProcedimientoNotaEnfermeriaInstance, field: 'padre', 'error')} required">
	<label for="padre">
		<g:message code="catProcedimientoNotaEnfermeria.padre.label" default="Padre" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="padre" name="padre.id" from="${mx.gob.inr.catalogos.CatRubroNotaEnfermeria.list()}" optionKey="id" required="" value="${catProcedimientoNotaEnfermeriaInstance?.padre?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catProcedimientoNotaEnfermeriaInstance, field: 'vista', 'error')} ">
	<label for="vista">
		<g:message code="catProcedimientoNotaEnfermeria.vista.label" default="Vista" />
		
	</label>
	<g:checkBox name="vista" value="${catProcedimientoNotaEnfermeriaInstance?.vista}" />
</div>

