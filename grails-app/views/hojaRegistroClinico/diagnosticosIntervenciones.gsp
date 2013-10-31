
<div style="position:fixed;background-color:rgb(190,214,248);top:0;left:0">
	<div class="nav" role="navigation">
			<ul>
				<li>
					<a class="guardar">
						<g:actionSubmit value="Guardar" action="guardarDiagnosticos"/>
					</a>
				</li>
				<li>
					<a href="#arribaHoja" class="arriba">IR ARRIBA</a>
				</li>
				<li>
					<a href="#abajoDiagnostico" class="abajo">IR ABAJO</a>
				</li>
			</ul>
	</div>
</div>




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