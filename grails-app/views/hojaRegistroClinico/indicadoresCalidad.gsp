<%@ page import="mx.gob.inr.utils.ConstantesHojaEnfermeria" %>

<div style="position:fixed;background-color:rgb(190,214,248);top:0;left:0">
	<div class="nav" role="navigation">
			<ul>
				<li>
					<a class="guardar">
						<g:actionSubmit value="Guardar" action="guardarIndicadores"/>
					</a>
				</li>
				<li>
					<a href="#arriba" class="arriba">IR ARRIBA</a>
				</li>
				<li>
					<a href="#abajoIndicador" class="abajo">IR ABAJO</a>
				</li>
			</ul>
	</div>
</div>

<%--Tabla de Prevension de caidas --%>

<table>
<caption>Prevencion de Caidas</caption>
<thead>
	<tr>
		<th>Descripcion</th>
		<th>Hora</th>
		<th></th>
	</tr>
</thead>
<g:each  in="${hojaInstance.tablaPrevencion}" var="procedimiento">
	<tr>
		<td><a style="cursor:pointer" onclick="mostrarPrevencion(${procedimiento.id})">${procedimiento.descripcion}</a></td>
		<td><g:textField id="horaPrevencion${procedimiento.id}" 
		name="horaPrevencion${procedimiento.id}" value="1" size="5"/></td>
		<td><input type="button" value="AGREGAR" class="agregar" onclick="guardarPrevencion(${procedimiento.id})"/>	</td>
	</tr>


</g:each>

</table>


<table>
<g:each in="${hojaInstance.rubrosIndicador}" var="rubro" status="i">

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
	<caption>Escala de Madox</caption>
	<thead>
		<tr>
			<th>MATUTINO</th>
			<th>VESPERTINO</th>
			<th>NOCTURNO</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td><g:textField name="madoxMatutino" size="7" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_ESCALA_MADDOX_MATUTINO},this.value)"
				value="${hojaInstance.escalaMadox?.getAt(0)?.otro}"/> </td>
			<td><g:textField name="madoxVespertino" size="7" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_ESCALA_MADDOX_VESPERTINO},this.value)" 
				value="${hojaInstance.escalaMadox?.getAt(1)?.otro}"/> </td>
			<td><g:textField name="madoxNocturno" size="7" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_ESCALA_MADDOX_NOCTURNO},this.value)" 
				value="${hojaInstance.escalaMadox?.getAt(2)?.otro}"/> </td>
		</tr>
	</tbody>

</table>

<table>
	<thead>
		<tr>
			<th>Tipo</th>
			<th>Fecha Instalacion</th>
			<th>Dias Consec.</th>
			<th>Material</th>
			<th>Calibre</th>
			<th>Globo</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td>Acceso Venoso</td>
			<td><g:textField name="fechaInstalacionV" size="10" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_FECHA_INSTALACION_V},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(0)?.fechaInstalacion}" /> </td>
			<td><g:textField name="diasConsec" size="5" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_DIAS_V},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(0)?.diasConsecutivos}"/> </td>
			<td></td>
			<td><g:textField name="calibre" size="7"
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_CALIBRE_V},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(0)?.calibre}"/> </td>
			<td></td>
		</tr>
		
		<tr>
			<td>Sonda Vesical<br>Instalada</td>
			<td><g:textField name="fechaInstalacionS" size="10" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_FECHA_INSTALACION_S},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(1)?.fechaInstalacion}"  /> </td>
			<td><g:textField name="diasConsec" size="5" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_DIAS_S},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(1)?.diasConsecutivos}"/> </td>
			<td><g:textField name="material" size="7" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_MATERIAL_S},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(1)?.material}"/> </td>
			<td><g:textField name="calibre" size="7" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_CALIBRE_S},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(1)?.calibre}"/> </td>
			<td><g:textField name="globo" size="7" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_GLOBO_S},this.value)" 
			value="${hojaInstance?.indicadores?.getAt(1)?.globo}"/> </td>
		</tr>
	</tbody>
</table>

<table>
	<caption>PLANEACION DEL ALTA</caption>
	<thead>
		<tr>
			<th>MATUTINO</th>
			<th>VESPERTINO</th>
			<th>NOCTURNO</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td><g:textArea name="planeacionMatutino"  rows="5" cols="18" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_DIAGNOSTICO_MATUTINO},this.value)" 
				value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(0)?.otro}"/> </td>
			<td><g:textArea name="planeacionVespertino" rows="5" cols="18" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_DIAGNOSTICO_VESPERTINO},this.value)" 
				value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(1)?.otro}"/> </td>
			<td><g:textArea name="planeacionNocturno" rows="5" cols="18" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_DIAGNOSTICO_NOCTURNO},this.value)" 
			value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(2)?.otro}"/> </td>
		</tr>
	</tbody>
</table>

<table>
	<caption>OBSERVACIONES</caption>
	<thead>
		<tr>
			<th>MATUTINO</th>
			<th>VESPERTINO</th>
			<th>NOCTURNO</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td><g:textArea name="observacionMatutino"  rows="5" cols="18" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_OBSERVACIONES_MATUTINO},this.value)" 
				value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(3)?.otro}"/> </td>
			<td><g:textArea name="observacionVespertino" rows="5" cols="18" 
				onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_OBSERVACIONES_VESPERTINO},this.value)" 
				value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(4)?.otro}"/> </td>
			<td><g:textArea name="observacionNocturno" rows="5" cols="18" 
			onblur="guardarTextTabla(${hojaInstance.id},${ConstantesHojaEnfermeria.P_OBSERVACIONES_NOCTURNO},this.value)" 
			value="${hojaInstance?.diagEnfermeriaObservaciones?.getAt(5)?.otro}" /> </td>
		</tr>
	</tbody>
</table>

<a name="abajoIndicador"></a>



