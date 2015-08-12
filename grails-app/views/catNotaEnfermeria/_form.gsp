<%@ page import="mx.gob.inr.catalogos.CatNotaEnfermeria" %>



<div class="fieldcontain ${hasErrors(bean: catNotaEnfermeriaInstance, field: 'descripcion', 'error')} ">
	<label for="descripcion">
		<g:message code="catNotaEnfermeria.descripcion.label" default="Descripcion" />
		
	</label>
	<g:textField name="descripcion" value="${catNotaEnfermeriaInstance?.descripcion}"/>
</div>

