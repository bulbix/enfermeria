$(document).ready(function() {
	
	
	$( ".hora" ).spinner({ min:1, max: 24 })	
	$("input:text.horaSigno").spinner({ min:1, max: 24 })	
	$( ".escalaDolorImagen" ).tooltip()
	
	$("#addSignosVitales").click(function(){
		
		var $tableBody = $('#tablaSignosVitales').find("tbody")
		$trLast = $tableBody.find("tr:last")
		
		var descripcion = $trLast.find("input:text.horaSigno").attr("id")
	
		//centinela
		var lastId = parseInt(descripcion.substring('horaSigno'.length))
			
		$trNew = $trLast.clone();
		var newId = lastId + 1		
		
		//alert(lastId)
		
		$trNew.find("input:text").val('')
		$trNew.find(".ui-spinner").
		replaceWith('<input type="text" class="horaSigno" id="horaSigno'+ newId +'" value="1" size="5"  onkeypress="return isNumberKey(event)"/>')
		
		
		$trNew.find("input:text.temperatura").attr("onblur",
		$trNew.find("input:text.temperatura").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		$trNew.find("input:text.cardiaca").attr("onblur",
		$trNew.find("input:text.cardiaca").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		$trNew.find("input:text.sistolica").attr("onblur",
		$trNew.find("input:text.sistolica").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		$trNew.find("input:text.diastolica").attr("onblur",
		$trNew.find("input:text.diastolica").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		$trNew.find("input:text.respiracion").attr("onblur",
		$trNew.find("input:text.respiracion").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		$trNew.find("input:text.gabinete").attr("onblur",
		$trNew.find("input:text.gabinete").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		
		
		$trNew.find("input:text.horaSigno").spinner({ min:1, max: 24 })
		
		$trLast.after($trNew)
		$trNew.find("input:text.horaSigno").focus()
		
	});
	
	
	var turnoActual = $( "#turno option:selected" ).val()
	
	switch(turnoActual){
		case "MATUTINO":			
			$("#dietaMatutino").attr("disabled",false)
			$("#dietaVespertino").attr("disabled",false)
			$("#dietaNocturno").attr("disabled",true)
			break
		case "VESPERTINO":
			$("#dietaMatutino").attr("disabled",true)
			$("#dietaVespertino").attr("disabled",false)
			$("#dietaNocturno").attr("disabled",false)
			break
		case "NOCTURNO":
			$("#dietaMatutino").attr("disabled",true)
			$("#dietaVespertino").attr("disabled",true)
			$("#dietaNocturno").attr("disabled",false)
			break	
	
	}
	
});

function guardarSignosVitales(){
	
}



function guardarEscalaDolor(dolor){
	
	var idHoja = $("#idHoja").val()
	var horaDolor = $("#horaDolor").val()
	
	$.getJSON("/enfermeria/signosVitales/guardarEscalaDolor",{dolor:dolor,idHoja:idHoja,horaDolor:horaDolor})
	.done(function( json ) {		
			$("#mensajeDolor").html(json.mensaje)
			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir la escala")
		})
	
}



function mostrarEscalaDolor(){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/signosVitales/mostrarEscalaDolor",{idHoja:idHoja})
	.done(function( json ) {
			$("#mostrarRegistros" ).html(json.html)
			$("#mensajeDolor").html('')
						
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
