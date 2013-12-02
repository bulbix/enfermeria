$(document).ready(function() {	
	
	$( ".hora" ).spinner({ min:1, max: 24 })
	correctSpinner($(".hora"))
	
	
	//El registro centinela con todos sus campos vacios
	if($("#tablaSignosVitales").find(".horaSigno").size() == 1){
		console.debug("Entro al registro")		
		if(todosVacios($("#tablaSignosVitales").find(".temperatura,.cardiaca,.sistolica,.diastolica,.respiracion,.gabinete"))){
			console.debug("ESTAN VACIOS TODOS LOS CAMPOS")
			$("input:text.horaSigno").spinner({ min:1, max: 24 })
			correctSpinner($("input:text.horaSigno"))
		}
		else{
			$('.horaSigno').attr("readonly",true);
		}
	}
	else{
		$('.horaSigno').attr("readonly",true);
	}
	
	existenSignosVitales($("#tablaSignosVitales").find(".temperatura,.cardiaca,.sistolica,.diastolica,.respiracion,.gabinete"))
	
		
	
	$(".temperatura").numericInput({ allowFloat: true })
	$(".cardiaca,.sistolica,.diastolica,.respiracion").numericInput()
	
	$( ".escalaDolorImagen" ).tooltip() 
	
	$("#addSignosVitales").click(function(){
		
		var $tableBody = $('#tablaSignosVitales').find("tbody")
		$trLast = $tableBody.find("tr:last")
		
		var descripcion = $trLast.find("input:text.horaSigno").attr("id")
	
		//centinela
		var lastId = parseInt(descripcion.substring('horaSigno'.length))
			
		$trNew = $trLast.clone();
		var newId = lastId + 1
		
		$trNew.find("input:text").val('')
		$trNew.find("input:text").attr('disabled',false)
		
		
		var cajaHora = '<input type="text" class="horaSigno" id="horaSigno'+ newId +'" value="1" size="1" maxlength="2"/>'
				
		//console.debug($trNew.find(".ui-spinner").size())
		
		if($trNew.find(".ui-spinner").size() >= 1){//la hora es un input spinner
			$trNew.find(".ui-spinner").replaceWith(cajaHora)
			console.debug('Es un input spinner')
		}
		else{ //una hora de solo lectura
			$trNew.find("input:text.horaSigno").replaceWith(cajaHora)
			console.debug('Es una caja de texto')
		}
		
		$trNew.find("input:text.horaSigno").val("1")
		$trNew.find("input:text.horaSigno").spinner({ min:1, max: 24 })
		correctSpinner($trNew.find("input:text.horaSigno"))
		
		
		$trNew.find("input:text.temperatura").attr("onblur",
		$trNew.find("input:text.temperatura").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.temperatura"))
		
		$trNew.find("input:text.cardiaca").attr("onblur",
		$trNew.find("input:text.cardiaca").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.cardiaca"))
		
		$trNew.find("input:text.sistolica").attr("onblur",
		$trNew.find("input:text.sistolica").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.sistolica"))
		
		$trNew.find("input:text.diastolica").attr("onblur",
		$trNew.find("input:text.diastolica").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.diastolica"))
		
		$trNew.find("input:text.respiracion").attr("onblur",
		$trNew.find("input:text.respiracion").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.respiracion"))
		
		$trNew.find("input:text.gabinete").attr("onblur",
		$trNew.find("input:text.gabinete").attr("onblur").replace("horaSigno"+lastId,"horaSigno"+newId))
		existenSignosVitales($trNew.find("input:text.gabinete"))
		
		$trNew.find(".temperatura").numericInput({ allowFloat: true })
		$trNew.find(".cardiaca,.sistolica,.diastolica,.respiracion").numericInput()	
		
		
		$trLast.after($trNew)
		$trNew.find("input:text.horaSigno").focus()	
		
	});
	
	
	var turnoActual = $( "#turno option:selected" ).val()
	
	switch(turnoActual){
		case "MATUTINO":			
			$("#dietaMatutino").attr("disabled",false)
			$("#horaDietaM").spinner("enable")
			$("#dietaVespertino").attr("disabled",false)
			$("#horaDietaV").spinner("enable")
			$("#dietaNocturno").attr("disabled",true)
			$("#horaDietaN").spinner("disable")
			break
		case "VESPERTINO":
			$("#dietaMatutino").attr("disabled",true)
			$("#horaDietaM").spinner("disable")
			$("#dietaVespertino").attr("disabled",false)
			$("#horaDietaV").spinner("enable")
			$("#dietaNocturno").attr("disabled",false)
			$("#horaDietaN").spinner("enable")
			break
		case "NOCTURNO":
			$("#dietaMatutino").attr("disabled",true)
			$("#horaDietaM").spinner("disable")
			$("#dietaVespertino").attr("disabled",true)
			$("#horaDietaV").spinner("disable")
			$("#dietaNocturno").attr("disabled",false)
			$("#horaDietaN").spinner("enable")
			break	
	
	}
	
	hojaSoloLectura()
	
	
	
});

function existenSignosVitales(cajaSigno){
	$(cajaSigno).keyup(function(e){
		
		/*if(existeHoraSignoVital($(this).parent().parent().find('.horaSigno'))){
			return
		}*/
		
		if(!todosVacios($(this).parent().parent().find(".temperatura,.cardiaca,.sistolica,.diastolica,.respiracion,.gabinete"))){
			try{
				$(this).parent().parent().find('.horaSigno').spinner( "destroy" );
				$(this).parent().parent().find('.horaSigno').attr("readonly",true);
			}
			catch(err){}
		}
		else{
			$(this).parent().parent().find('.horaSigno').spinner({ min:1, max: 24 })
			correctSpinner($("input:text.horaSigno"))
			$(this).parent().parent().find('.horaSigno').attr("readonly",false);
		}		
		
	})
	
}


/*function existeHoraSignoVital(cajaHora){
	
	$("#tablaSignosVitales").find(".horaSigno").each(function() {
		console.debug("Renglon Actual " + $(cajaHora).val())
		console.debug("Otros renglones " + $(this).val())
		if(this != cajaHora){			
			if($(this).val() == $(cajaHora).val()){
				mostrarMensaje("Ya existe un renglon con hora " + $(cajaHora).val() + ", cambie la hora",'error' )
				$(cajaHora).parent().parent().find("input").val('')
				return true
			}
		}		
	})
	
	return false
}*/

function guardarSignoVital(idHoja,idProcedimiento,valor,hora,modificarHora, caja){
	
	procedimiento =[]
	procedimiento[7] = 'Temperatura';
	procedimiento[8] = 'Frecuancia Cardiaca';
	procedimiento[9] = 'T.A. Sistolica';
	procedimiento[10] = 'T.A. Diastolica';
	procedimiento[416] = 'Frec Resp';
	procedimiento[425] = 'Lab y Gab';
	
	if(hora == ''){
		mostrarMensaje("Indique una hora",'error' )
		$(caja).parent().parent().find("input").val('')
		return
	}	
		
	var mensaje = "Signo vital " + procedimiento[idProcedimiento] + " con hora " + hora  + " guardado"	

	if(valor == ''){
		mensaje = "Signo vital " + procedimiento[idProcedimiento]	 + " con hora " + hora  + " eliminado"
	}

	guardarTextTablaConHora(idHoja,idProcedimiento,valor,hora,modificarHora,'mensajeSigno',mensaje)	

	
}

function guardarEscalaDolor(dolor){
	
	var idHoja = $("#idHoja").val()
	var horaDolor = $("#horaDolor").val()
	
	$.getJSON("/enfermeria/signosVitales/guardarEscalaDolor",{dolor:dolor,idHoja:idHoja,horaDolor:horaDolor})
	.done(function( json ) {		
			mostrarMensaje(json.mensaje,json.status)
			
		})
		.fail(function() {
			mostrarMensaje("Ocurrio un error al añadir la escala","error")
	})	
}

function mostrarEscalaDolor(){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.blockUI({ message: '<h1>Mostrando Escala Dolor...</h1>' });
	 $.getJSON("/enfermeria/signosVitales/mostrarEscalaDolor",{idHoja:idHoja})
	.done(function( json ) {
			$("#mostrarRegistros" ).html(json.html)
			hojaSoloLectura()			
			$("#mostrarRegistros").dialog('option', 'title','Escala del Dolor');
			tablaFloatHead("#tablaDolor")
			$( "#mostrarRegistros" ).dialog( "open" );
			$.unblockUI()
						
		})
		.fail(function() {	
			mostrarMensaje("Ocurrio un error al mostrar la escala","error")
		})	
	
	 
	
}

function borrarDetalleDolor(idRegistro){
	
	 	$.getJSON("/enfermeria/signosVitales/borrarDetalleDolor", {idRegistro:idRegistro})
	 	.done(function( json ) {
	 		$( "#rowDolor"+idRegistro ).remove()		
								
		})
		.fail(function() {
			mostrarMensaje("Ocurrio un error al borrar la escala","error")
		})
}

function borrarAllDetalleDolor(){
	
	mostrarConfirmacion("Esta seguro de eliminar sus registros?", function(){
		
		var idHoja = $("#idHoja").val()
		
		$.getJSON("/enfermeria/signosVitales/borrarAllDetalleDolor", {idHoja:idHoja})
	 	.done(function( json ) {
	 		mostrarEscalaDolor()
								
		})
		.fail(function() {
			mostrarMensaje("Ocurrio un error al borrar la escala","error")
		})
	})
	
}

function guardarDieta(idHoja,idProcedimiento,valor,hora,modificarHora){
	
	procedimiento =[]
	procedimiento[426] = 'generalizada';
	procedimiento[116] = 'matutina';
	procedimiento[117] = 'vespertina';
	procedimiento[118] = 'nocturna';
	
	
	var mensaje = ""
	
	if(hora != -1){	
		mensaje = "Dieta " + procedimiento[idProcedimiento] + " con hora " + hora  + " guardado"
	}
	else{
		mensaje = "Dieta " + procedimiento[idProcedimiento] + " guardado"
	}
	
	if(valor == ''){
		if(hora != -1){	
			mensaje = "Dieta " + procedimiento[idProcedimiento]	 + " con hora " + hora  + " eliminado"
		}
		else{
			mensaje = "Dieta " + procedimiento[idProcedimiento] + " eliminado"
		}
	}
	
	guardarTextTablaConHora(idHoja,idProcedimiento,valor,hora,modificarHora, 'mensajeDieta', mensaje)
	
}

function todosVacios(selector) {
	
	var all = true;
	  $(selector).each( function(index, value) { 
	    all = all & ($(value).val().length == 0);
	  });
	return all;
	
	
}
	
