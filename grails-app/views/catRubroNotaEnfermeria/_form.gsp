<%@ page import="mx.gob.inr.catalogos.CatRubroNotaEnfermeria" %>



<div class="fieldcontain ${hasErrors(bean: catRubroNotaEnfermeriaInstance, field: 'descripcion', 'error')} ">
	<label for="descripcion">
		<g:message code="catRubroNotaEnfermeria.descripcion.label" default="Descripcion" />
		
	</label>
	<g:textField name="descripcion" value="${catRubroNotaEnfermeriaInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catRubroNotaEnfermeriaInstance, field: 'padre', 'error')} required">
	<label for="padre">
		<g:message code="catRubroNotaEnfermeria.padre.label" default="Padre" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="padre" name="padre.id" from="${mx.gob.inr.catalogos.CatSegmentoNotaEnfermeria.list()}" optionKey="id" required="" value="${catRubroNotaEnfermeriaInstance?.padre?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catRubroNotaEnfermeriaInstance, field: 'tipo', 'error')} ">
	<label for="tipo">
		<g:message code="catRubroNotaEnfermeria.tipo.label" default="Tipo" />
		
	</label>
	<g:textField name="tipo" value="${catRubroNotaEnfermeriaInstance?.tipo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: catRubroNotaEnfermeriaInstance, field: 'vista', 'error')} ">
	<label for="vista">
		<g:message code="catRubroNotaEnfermeria.vista.label" default="Vista" />
		
	</label>
	<g:checkBox name="vista" value="${catRubroNotaEnfermeriaInstance?.vista}" />
</div>

