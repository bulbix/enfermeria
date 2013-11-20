
<table id="tblBusqueda" class="busqueda">
			<tr>
				<td colspan="6"><label for="artauto">Descripción
						Articulo</label> <g:textField name="artauto" style="width: 700px;" />
				</td>
			</tr>
			<tr>
				<td><label for="insumo">Clave</label> <g:textField
						name="insumo" size="3" /></td>
				<td><label for="unidad">Unidad</label> <g:textField
						name="unidad" readonly="true" size="15" /></td>
				<td><label for="costo">Costo</label> <g:textField
						name="costo" readonly="true" size="10" />
				</td>
				<td><label for="cantidad">Cantidad</label> <g:textField
						name="cantidad" size="3" /></td>
			</tr>
</table>


<table class="busqueda">
		<thead>
			<tr>
				<td><label>Clave</label></td>
				<td><label>Descripcion</label></td>
				<td><label>Unidad</label></td>
				<td><label>Costo</label></td>				
				<td><label>Cantidad</label></td>

			</tr>

		</thead>

		<tbody>
			<tr>
				<td><label id="clavelast"></label></td>
				<td><label id="deslast"></label></td>
				<td><label id="unidadlast"></label></td>
				<td><label id="costolast"></label></td>				
				<td><label id="cantidadlast"></label></td>

			</tr>

		</tbody>
	</table>
	
<g:if test="${true}">
	<input type="button" id="btnActualizar" value="Actualizar" class="busqueda" />
	<input type="button" id="btnBorrar" value="Borrar" class="busqueda" />
</g:if>

<form id="formDetalle">
		<div class="list">
			<table id="detalle"></table>
			<div id="pager"></div>
		</div>
</form>