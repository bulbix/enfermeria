<%@ page import="mx.gob.inr.utils.ConstantesHojaEnfermeria" %>

<g:barraNavegacion tagAbajo="abajoValoracion"></g:barraNavegacion>


<table>
<g:each in="${hojaInstance.rubrosValoracion}" var="rubro" status="i">

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

<table>

	<tr>						
		<th colspan="2">
			REQUISITOS
		</th>					
	</tr>

	<tr>
		<td>
			<label>DE DESARROLLO</label>							
			<g:textArea name="requisito"  
			rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(0)?.otro}" 
			onblur="guardarTextTabla(${hojaInstance?.id},${ConstantesHojaEnfermeria.P_DESARROLLO},this.value)" />
		<td>
		
		<td>
			<label>DE DESVIACION DE LA SALUD</label>							
			<g:textArea name="requisito"  rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(1)?.otro}" 
			onblur="guardarTextTabla(${hojaInstance?.id},${ConstantesHojaEnfermeria.P_DESVIACION_SALUD},this.value)"/>
		</td>	
	</tr>

</table>

<a name="abajoValoracion"></a>	