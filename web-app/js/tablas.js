function guardarCheckTabla(idHoja,idProcedimiento,turno,valor){
	
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarCheckTabla",
		{idHoja:idHoja,idProcedimiento:idProcedimiento,turno:turno,valor:valor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)		
		})
		.fail(function() {
			//alert("Ocurrio un error al añadir la escala")
		})
	
	
}

function guardarTextTabla(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarTextTabla",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				//alert("Ocurrio un error al añadir la escala")
			})
			
}


function guardarTextTablaSinBorrar(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarTextTablaSinBorrar",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				//alert("Ocurrio un error al añadir la escala")
			})
			
}

function seleccionarChecks(selector, check){
	
	//$("input[type='checkbox'][name="+selector+"]").attr('checked', check)
	$("input[type='checkbox'][name="+selector+"]").each(function(i) {
		$(this).attr('checked', check);		
		$(this).trigger('change');
	});
	
	
}
