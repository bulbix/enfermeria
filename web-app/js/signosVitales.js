$(document).ready(function() {
	
	$("#addSignosVitales").click(function(){
		
		var $tableBody = $('#tablaSignosVitales').find("tbody")
		$trLast = $tableBody.find("tr:last")
		$trNew = $trLast.clone()
		
		$trNew.find("input:text").val('')

		$trLast.after($trNew);
		
	});
	
	 /*$('#tablaSignosVitales').freezeTable({
	        'autoHeight': true,
	        'autoHeightMarginBottom'    : 20
	 });*/	
	
	
	
	
});



function guardarEscalaDolor(dolor){
	
	var idHoja = $("#idHoja").val()
	var horaDolor = $("#horaDolor").val()
	
	$.getJSON("/enfermeria/signosVitales/guardarEscalaDolor",{dolor:dolor,idHoja:idHoja,horaDolor:horaDolor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)
			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir la escala")
		})
	
}



function mostrarEscalaDolor(){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/signosVitales/mostrarEscalaDolor",{idHoja:idHoja})
	.done(function( json ) {
			$( "#mostrarRegistros" ).html(json.html)			
						
		})
		.fail(function() {			
		})	
	
	
	 $( "#mostrarRegistros" ).dialog( "open" );
	
}

function borrarDetalleDolor(idRegistro){
	
	 $.getJSON("/enfermeria/signosVitales/borrarDetalleDolor", {idRegistro:idRegistro})
	 	.done(function( json ) {
	 		$( "#rowDolor"+idRegistro ).remove()		
								
		})
		.fail(function() {
			
	})
}
