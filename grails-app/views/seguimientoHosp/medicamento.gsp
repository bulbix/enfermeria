<g:if test="${!soloLectura}">
	<table id="tblBusqueda" class="busqueda">
				<tr>
					<td colspan="6"><label for="artauto">Descripción
							Medicamento</label> <g:textField name="artauto" style="width: 700px;" />
					</td>
				</tr>
				<tr>
					<td><label for="insumo">Clave</label> <g:textField
							name="insumo" size="3" /></td>
					<td><label for="unidad">Unidad</label> <g:textField
							name="unidad" readonly="true" size="15" /></td>
					<td><label for="precioUnitario">Precio Unitario</label> <g:textField
							name="precioUnitario" readonly="true" size="10" />
					</td>
					<td><label for="cantidad">Cantidad</label> <g:textField
							name="cantidad" size="3" /></td>
				</tr>
	</table>
	
	
	<table class="busqueda">
			<caption>Ultimo medicamento capturado</caption>
			<thead>
				<tr>
					<th><label>Clave</label></th>
					<th><label>Descripcion</label></th>
					<th><label>Unidad</label></th>
					<th><label>Precio Unitario</label></th>				
					<th><label>Cantidad</label></th>
	
				</tr>
	
			</thead>
	
			<tbody>
				<tr>
					<td><label id="clavelast"></label></td>
					<td><label id="deslast"></label></td>
					<td><label id="unidadlast"></label></td>
					<td><label id="precioUnitariolast"></label></td>				
					<td><label id="cantidadlast"></label></td>
	
				</tr>
	
			</tbody>
		</table>
		
	
	<input type="button" id="btnActualizar" value="Actualizar Medicamento" class="busqueda" />
	<input type="button" id="btnBorrar" value="Borrar Medicamento" class="busqueda" />	
</g:if>

<label for="importeTotal">Importe Total</label><input type="text" id="importeTotal" style="font-weight: bold" readonly size="15"  />
<form id="formDetalle">
		<div class="list">
			<table id="detalle"></table>
			<div id="pager"></div>
		</div>
</form>