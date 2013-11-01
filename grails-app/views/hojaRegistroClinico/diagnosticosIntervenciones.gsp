
<g:barraNavegacion tagAbajo="abajoDiagnostico"></g:barraNavegacion>

<table>
<g:each in="${hojaInstance.rubrosDiagnostico}" var="rubro" status="i">

	<g:if test="${i % 2 == 0}">
		<tr>
			<td>
				<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" 
				titulo="${rubro.descripcion}" turno="${hojaInstance.turnoActual}" mostrar="${rubro.vista}" tipo="${rubro.tipo}"/>		
			</td>
	</g:if>
	<g:else>
		<td>
			<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" 
			titulo="${rubro.descripcion}" turno="${hojaInstance.turnoActual}" mostrar="${rubro.vista}" tipo="${rubro.tipo}"/>		
		</td>
		</tr>
	</g:else>	
</g:each>

</table>

<a name="abajoDiagnostico"></a>