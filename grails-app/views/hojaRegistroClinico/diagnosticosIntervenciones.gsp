<g:actionSubmit value="Guardar" action="guardarDiagnosticos"/>

<table>
<g:each in="${hojaInstance.rubrosDiagnostico}" var="rubro" status="i">

	<g:if test="${i % 2 == 0}">
		<tr>
			<td>
				<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" titulo="${rubro.descripcion}" turno="MATUTINO"/>		
			</td>
	</g:if>
	<g:else>
		<td>
			<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" titulo="${rubro.descripcion}" turno="MATUTINO"/>		
		</td>
		</tr>
	</g:else>	
</g:each>

</table>