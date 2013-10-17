$(document).ready(function() {
	
	$("#addSignosVitales").click(function(){
		
		var $tableBody = $('#tablaSignosVitales').find("tbody"),
		$trLast = $tableBody.find("tr:last"),
		$trNew = $trLast.clone();

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
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarEscalaDolor",{dolor:dolor,idHoja:idHoja,horaDolor:horaDolor})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)
			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir la escala")
		})
	
}
