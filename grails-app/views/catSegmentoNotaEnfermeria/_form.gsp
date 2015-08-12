<%@ page import="mx.gob.inr.catalogos.CatSegmentoNotaEnfermeria" %>



<div class="fieldcontain ${hasErrors(bean: catSegmentoNotaEnfermeriaInstance, field: 'descripcion', 'error')} ">
	<label for="descripcion">
		<g:message code="catSegmentoNotaEnfermeria.descripcion.label" default="Descripcion" />
		
	</label>
	<g:textField name="descripcion" value="${catSegmentoNotaEnfermeriaInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catSegmentoNotaEnfermeriaInstance, field: 'padre', 'error')} required">
	<label for="padre">
		<g:message code="catSegmentoNotaEnfermeria.padre.label" default="Padre" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="padre" name="padre.id" from="${mx.gob.inr.catalogos.CatNotaEnfermeria.list()}" optionKey="id" required="" value="${catSegmentoNotaEnfermeriaInstance?.padre?.id}" class="many-to-one"/>
</div>

