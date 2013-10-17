function guardarCheckTabla(idHoja,idProcedimiento,turno,valor){
	
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarCheckTabla",
		{idHoja:idHoja,idProcedimiento:idProcedimiento,turno:turno,valor:valor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)		
		})
		.fail(function() {
			alert("Ocurrio un error al añadir la escala")
		})
	
	
}