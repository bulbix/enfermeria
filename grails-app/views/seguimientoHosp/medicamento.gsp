
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
						name="cantidad" name="cantidad" size="3" /></td>
			</tr>
</table>

<form id="formDetalle">
		<div class="list">
			<table id="detalle"></table>
			<div id="pager"></div>
		</div>
</form>