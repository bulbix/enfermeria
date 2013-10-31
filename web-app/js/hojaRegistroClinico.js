$(document).ready(function() {
	
	$.datepicker.setDefaults($.datepicker.regional['es']);
	
	$( "#tabs" ).tabs();
	
	if($("#idHoja").val() == ''){
		$( "#tabs" ).tabs( "option", "disabled", [1,2,3,4,5] );		
		$("#tablaCaptura").show()
		$("#tablaLectura").hide()
		$("#tablaFiltro").show()
	}
	else{
		$( "#tabs" ).tabs( "option", "disabled", [] );
		$("#abrir").show()		
		$("#tablaCaptura").hide()
		$("#tablaLectura").show()
		$("#tablaFiltro").hide()
	}	
	
	$( "#mostrarHojas" ).dialog({
		  position: 'top',
	      autoOpen: false,
	      width:"900px"
	});
	
	$( "#mostrarFirma" ).dialog({
	      autoOpen: false,
	      width:"600px"	     
	});
	
	$( "#mostrarRegistros" ).dialog({		  
	      autoOpen: false,
	      width:"800px"
	});
	
	$("#fechaElaboracion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	}).attr('readonly', 'readonly');	
	
	cargarServicios()
	validar()
	
	autoCompletePaciente(function(){
		
		var idPaciente = $("#idPaciente").val()
		
		$.getJSON("/enfermeria/autoComplete/consultarDatosPaciente",{idPaciente:idPaciente})
		.done(function( json ) {
			
				$("#idAdmision").val(json.idAdmision)
				$("#edad").html(json.edad)
				$("#sexo").html(json.sexo)
				$("#religion").html(json.religion)
				$("#cama").html(json.cama)
				$("#diasHosp").html(json.diasHosp)
				$("#servicio").html(json.servicio)
				$("#diagnostico").html(json.diagnostico)
				$("#peso").val(json.peso)
				$("#talla").val(json.talla)
				
				$("#abrir").show()
				
			})
			.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
			})
	})
	
});

function validar(){
	$("#formHojaEnfermeria").validate({
		
		ignore: [],
		
        rules: {
        	fechaElaboracion: {required:true,validateDate:true,dateToday:true},
        	idPaciente:{required:true},
        	peso: {required:true, number:true},
        	talla: {required:true, number:true}
             
        },
		messages: {
			fechaElaboracion:{required:"Requerido"},
			idPaciente:{required:'No ha seleccionado paciente'},
			peso:{required:"Requerido",number:"Numerico"},
			talla:{required:"Requerido",number:"Numerico"}
		}
  });
}



function cargarServicios(){
	
	$("#pisos").change(function(){
		
		var idArea = $( "#pisos option:selected" ).val()
		
		$.getJSON("/enfermeria/autoComplete/consultarServicios",{idArea:idArea})
		.done(function( json ) {
				
				$('#servicios option').remove();
				 $("<option/>").attr("value","-1").text("[T O D O S]").appendTo($("#servicios"));
				
		        for (var i = 0; i < json.length; i++) {
		            $("<option/>").attr("value", json[i].id).text(json[i].descripcion).appendTo($("#servicios"));
		        }
				
			})
			.fail(function() {
				alert("Ocurrio un error al cargar los servicios")
			})
	});	
	
}

function guardarHojaTurno(){
	
	var frm = $("#formHojaEnfermeria");
    var dataHoja = JSON.stringify(frm.serializeObject());
    var jsonHoja = JSON.parse(dataHoja);
    
    /*if(jsonHoja.idPaciente == ''){
			$("#mensaje").html("No ha seleccionado paciente")
			return
	}*/
    
    
    if($("#formHojaEnfermeria").valid()){
		 $.getJSON("/enfermeria/hojaRegistroClinico/guardarHojaTurno",
				 {dataHoja:dataHoja}).done(function( json ) {
					 
					 
					 switch(json.status){
					 	case 'firmarHoja':
					 		 $( "#mostrarFirma" ).html(json.html)
							 $( "#mostrarFirma" ).dialog( "open" );
					 		break;
					 	case 'existeHoja':
					 		
					 		if(jsonHoja.idHoja == ''){
					 			$("#mensaje").html("La fecha de elaboracion " + 
						 				jsonHoja.fechaElaboracion +" ya tiene registro, dale click Abrir Hoja y asocie turno")
					 		}
					 		else{
					 			$("#mensaje").html("Alergias Comorbilidad salvado correctamente")
					 		}	 			
					 	
					 }
					 
		})
		.fail(function() {			
		})
    }
	
}

function mostrarHojas(){	
	 
	 var idPaciente = $("#idPaciente").val()
	 
	 
	 $.getJSON("/enfermeria/hojaRegistroClinico/consultarHojas",
			 {idPaciente:idPaciente})
	.done(function( json ) {
		$( "#mostrarHojas" ).html(json.html)
		$( ".jefe, .supervisor").tooltip()
						
	})
	.fail(function() {
			alert("Ocurrio un error al mostrar las hojas")
	})	
	
	
	 $( "#mostrarHojas" ).dialog( "open" );	
	
}

function mostrarFirma(idHoja,tieneUsuario,tipoUsuario){
	
	var turnoAsociar = $('#turnoAsociar').val()	
	var usuarioFirma = $('#usuarioFirma')	
	
	 $.getJSON("/enfermeria/hojaRegistroClinico/mostrarFirma",
			 {idHoja:idHoja,turnoAsociar:turnoAsociar,tieneUsuario:tieneUsuario,tipoUsuario:tipoUsuario})
			 .done(function( json ) {
				 
				 switch(json.status){
				 	case 'cargarHoja':
				 		
				 		redirectConsultarHoja(idHoja,turnoAsociar,
								"Hoja cargada satisfactoriamente")
				 		break
				 	case 'firmarHoja':
				 		 $( "#mostrarFirma" ).html(json.html)
						 $( "#mostrarFirma" ).dialog( "open" );
				 		autoComplete('#usuarioFirma',
				 		"/enfermeria/autoComplete/consultarEnfermeras",'#idUsuarioFirma',
				 		function(){},3)	
				 		
				 }
				 
	})
	.fail(function() {			
	})	
	 
	
	
}



function firmarHoja(idHoja){
	
	var turnoAsociar = $('#turnoAsociar').val()	
	var passwordFirma = $('#passwordFirma').val()	
	var idUsuarioFirma = $('#idUsuarioFirma').val()
	var tipoUsuarioFirma = $('#tipoUsuarioFirma').val()	
	
	var frm = $("#formHojaEnfermeria");
    var dataHoja = JSON.stringify(frm.serializeObject());
    var jsonHoja = JSON.parse(dataHoja);	
    
    if(turnoAsociar == undefined){
    	turnoAsociar = jsonHoja.turno
    }
	
	$.getJSON("/enfermeria/hojaRegistroClinico/firmarHoja",
			 {idHoja:idHoja,passwordFirma:passwordFirma,
				turnoAsociar:turnoAsociar,dataHoja:dataHoja,
				idUsuarioFirma:idUsuarioFirma,tipoUsuarioFirma:tipoUsuarioFirma}).
			 done(function( json ) {			
				 if(json.firmado==true){
					 idHoja = json.idHoja
					 $( "#mostrarFirma" ).dialog( "close" );
					 redirectConsultarHoja(idHoja,turnoAsociar,
							"Se ha firmado el turno " + turnoAsociar +" correctamente")
				 }
				 else{
					 alert('No coincide la firma')
				 }
				 
	})
	.fail(function() {
		alert('error')
	})
	
	
}


