$(document).ready(function() {
	
	$(function() {
	    $( "#tabs" ).tabs();
	});
	
	$( "#mostrarRegistros" ).dialog({
	      autoOpen: false,
	      width:"600px"	     
	});
	
	$( "#mostrarFirma" ).dialog({
	      autoOpen: false,
	      width:"600px"	     
	});
	
	$("#fechaElaboracion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	});
	
	cargarServicios()
	
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
				
			})
			.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
			})
	})
	
});



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


function mostrarHojas(){	
	 
	 var idPaciente = $("#idPaciente").val()
	 
	 
	 $.getJSON("/enfermeria/hojaRegistroClinico/consultarHojas",
			 {idPaciente:idPaciente})
	.done(function( json ) {
	$( "#mostrarRegistros" ).html(json.html)			
						
		})
	.fail(function() {
			alert("Ocurrio un error al mostrar las hojas")
	})	
	
	
	 $( "#mostrarRegistros" ).dialog( "open" );	
	
}


function mostrarFirma(idHoja){
	
	var turno = $('#turnoAsociar').val()
	
	 $.getJSON("/enfermeria/hojaRegistroClinico/mostrarFirma",
			 {idHoja:idHoja,turno:turno}).done(function( json ) {
				 
				 if(json.status == 'cargarHoja'){
					 window.location.href = '/enfermeria/hojaRegistroClinico/consultarHoja?idHoja='
						 +idHoja+"&turnoActual="+turno
				 }
				 else{
					 $( "#mostrarFirma" ).html(json.html)
					 $( "#mostrarFirma" ).dialog( "open" );					 
				 }			 
				 
	})
	.fail(function() {			
	})	
	 
	
	
}


function firmarHoja(idHoja){
	
	var passwordFirma = $('#passwordFirma').val()
	var turno = $('#turnoAsociar').val()
	
	
	$.getJSON("/enfermeria/hojaRegistroClinico/firmarHoja",
			 {idHoja:idHoja,passwordFirma:passwordFirma,turno:turno}).done(function( json ) {
				 //alert(json.firmado)
				 if(json.firmado==true){
					 $( "#mostrarFirma" ).dialog( "close" );
					 window.location.href = '/enfermeria/hojaRegistroClinico/consultarHoja?idHoja='
						 +idHoja+"&turnoActual="+turno
				 }
				 else{
					 alert('No coincide la firma')
				 }
				 
	})
	.fail(function() {			
	})
	
	
}


