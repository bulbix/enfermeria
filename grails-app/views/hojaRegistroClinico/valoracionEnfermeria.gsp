<%@ page import="mx.gob.inr.utils.ConstantesHojaEnfermeria" %>

<g:barraNavegacion tagAbajo="abajoValoracion"></g:barraNavegacion>


<label style="color:blue;font-size:18px">Todos los procedimientos seran salvados de manera automatica</label>

<div class="container">
	<g:each in="${hojaInstance.rubrosValoracion}" var="rubro" status="i">
		<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" 
		titulo="${rubro.descripcion}" turno="${hojaInstance.turnoActual}" mostrar="${rubro.vista}" tipo="${rubro.tipo}"/>	
	</g:each>
</div>

<table>
	<caption>REQUISITOS</caption>
	<tr>
		<td>
			<label>DE DESARROLLO</label>							
			<g:textArea name="requisito" id="requisitoDesarrollo" 
			rows="5" cols="30" value="${hojaInstance.requisitos?.getAt(0)?.otro}" 
			onblur="guardarTextTabla(${hojaInstance?.id},${ConstantesHojaEnfermeria.P_DESARROLLO},this.value)" />
		<td>
		
		<td>
			<label>DE DESVIACION DE LA SALUD</label>							
			<g:textArea name="requisito" id="requisitoSalud"  rows="5" cols="30" value="${hojaInstance.requisitos?.getAt(1)?.otro}" 
			onblur="guardarTextTabla(${hojaInstance?.id},${ConstantesHojaEnfermeria.P_DESVIACION_SALUD},this.value)"/>
		</td>	
	</tr>

</table>

<a name="abajoValoracion"></a>	