
<input type="button" onclick="guardarHojaTurno()" value="Guardar/Firmar"/>

<table>
	<tr>
		<td>
			<label for="comorbilidad">Comorbilidad:</label>
			<label for="has">HAS</label><g:checkBox name="has" value="${hojaInstance.has}"  />
			<label for="dm">DM</label><g:checkBox name="dm" value="${hojaInstance.dm}" />
			<label for="nef">NEF</label> <g:checkBox name="nef" value="${hojaInstance.nef}" />
			<label for="ic">IC</label> <g:checkBox name="ic" value="${hojaInstance.ic}" />
			<label for="ir">IR</label> <g:checkBox name="ir" value="${hojaInstance.ir}" />
		</td>
	</tr>
</table>

<table>
	<tr>

		<td>
			<table>
				<tr>
					<td>
					<label for="peso">Peso:</label>
					<g:field type="number" name="peso" value="${hojaInstance.peso}" size="5" /> kg.
					
					</td>
				</tr>
				<tr>
					<td>
					<label for="talla">Talla:</label>
					<g:field type="number" name="talla" value="${hojaInstance.talla}" size="5" step="0.01" /> m.
					</td>
				</tr>

			</table>
		</td>



		<td><label for="alergias">Alergias:</label> <g:textField
				name="alergias" value="${hojaInstance.alergias}"  /></td>

		<td><label for="otro">Otro:</label> <g:textField name="otros"
				value="${hojaInstance.otros}"  /></td>
	</tr>

</table>
