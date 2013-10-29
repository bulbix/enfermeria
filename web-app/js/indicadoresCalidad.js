$(document).ready(function() {
	
	$("#fechaInstalacion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	});
});

function guardarPrevencion(id){
	
	var hora = $("#horaPrevencion"+id).val()
	
	$.getJSON("/enfermeria/indicadoresCalidad/guardarPrevencion",
	{idProcedimiento:id,hora:hora,idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el "+ id)
		})		
}

function mostrarPrevencion(id){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/indicadoresCalidad/consultarDetallePrevencion",
	 {idHoja:idHoja,idProcedimiento:id})
	 .done(function( json ) {
			$( "#mostrarRegistros" ).html(json.html)
		})
		.fail(function() {
			alert("Ocurrio un error al mostrar el "+id )
		})	
	
	
	 $( "#mostrarRegistros" ).dialog( "open" );
	
	
}
