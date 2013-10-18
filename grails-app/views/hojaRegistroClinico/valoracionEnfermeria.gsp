<g:submitToRemote value="Guardar" action="guardarValoracionEnfermeria" update="mensaje" />

<table>
<g:each in="${hojaInstance.rubrosValoracion}" var="rubro" status="i">

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

<table>

	<tr>						
		<th colspan="2">
			REQUISITOS
		</th>					
	</tr>

	<tr>
		<td>
			<label>DE DESARROLLO</label>							
			<g:textArea name="requisito"  rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(0)?.otro}" />
		<td>
		
		<td>
			<label>DE DESVIACION DE LA SALUD</label>							
			<g:textArea name="requisito"  rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(1)?.otro}"/>
		</td>	
	</tr>

</table>	