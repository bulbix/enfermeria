function guardarCheckTabla(idHoja,idProcedimiento,turno,valor){
	
	
	$.getJSON("/enfermeria/util/guardarCheckTabla",
		{idHoja:idHoja,idProcedimiento:idProcedimiento,turno:turno,valor:valor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)		
		})
		.fail(function() {
			//alert("Ocurrio un error al añadir la escala")
		})
}


function guardarRadioTabla(idHoja,idProcedimiento,valor){
	
	
	$.getJSON("/enfermeria/util/guardarRadioTabla",
		{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)		
		})
		.fail(function() {
			
		})
	
	
}

function borrarRadioTabla(idHoja,idProcedimiento,radio){	
	
	$.getJSON("/enfermeria/util/borrarRadioTabla",
		{idHoja:idHoja,idProcedimiento:idProcedimiento})
	.done(function( json ) {
			$('input:radio[name="'+radio+'"]').attr("checked",false)
			$("#mensaje").html(json.mensaje)		
		})
		.fail(function() {
			
		})
	
	
}

function seleccionarChecks(selector, check){	
	
	$("input[type='checkbox'][name="+selector+"]").each(function(i) {
		//$(this).unbind( "change" );
		$(this).prop('checked', check);
		$(this).trigger('change');
	});
	
	
}
