<g:actionSubmit value="Guardar" action="guardarValoracionEnfermeria"/>

<table>
<g:each in="${hojaInstance.rubrosValoracion}" var="rubro" status="i">

	<g:if test="${i % 2 == 0}">
		<tr>
			<td>
				<g:tablaValoracion idhoja="${hojaInstance.id}" idrubro="${rubro.id}" titulo="${rubro.descripcion}" turno="MATUTINO"/>		
			</td>
	</g:if>
	<g:else>
		<td>
			<g:tablaValoracion idhoja="${hojaInstance.id}" idrubro="${rubro.id}" titulo="${rubro.descripcion}" turno="MATUTINO"/>		
		</td>
		</tr>
	</g:else>	
</g:each>

</table>