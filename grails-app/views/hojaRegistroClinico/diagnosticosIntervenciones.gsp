
<g:barraNavegacion tagAbajo="abajoDiagnostico" filtroTabla="true"></g:barraNavegacion>

<label style="color:blue;font-size:18px">Todos los procedimientos seran salvados de manera automatica</label>

<div class="container">
	<g:each in="${hojaInstance.rubrosDiagnostico}" var="rubro" status="i">
		<g:tablaEnfermeria idhoja="${hojaInstance.id}" idrubro="${rubro.id}" 
		titulo="${rubro.descripcion}" turno="${hojaInstance.turnoActual}" mostrar="${rubro.vista}" tipo="${rubro.tipo}"/>
	</g:each>
</div>


<table >
	<caption>FIN DIAGNOSTICOS</caption>

</table>

<a name="abajoDiagnostico"></a>

