<div style="position:fixed;background-color:rgb(190,214,248);top:0;left:0">
	<div class="nav" role="navigation">
			<ul>
				<li>
					<a class="guardar">
					<g:submitToRemote value="Guardar" controller="valoracionEnfermeria" action="guardarValoracionEnfermeria" update="mensaje" />
					</a>
				</li>
				<li>
					<a href="#arribaHoja" class="arriba">IR ARRIBA</a>
				</li>
				<li>
					<a href="#abajoValoracion" class="abajo">IR ABAJO</a>
				</li>
			</ul>
	</div>
</div>




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
			<g:textArea name="requisito"  rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(0)?.otro}" />
		<td>
		
		<td>
			<label>DE DESVIACION DE LA SALUD</label>							
			<g:textArea name="requisito"  rows="5" cols="18" value="${hojaInstance.requisitos?.getAt(1)?.otro}"/>
		</td>	
	</tr>

</table>

<a name="abajoValoracion"></a>	