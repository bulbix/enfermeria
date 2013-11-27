$(document).ready(function() {	
	
	$( "#tabs" ).tabs({});
	
	if($("#idSeguimiento").val() == ''){
		//$( "#tabs" ).tabs( "option", "disabled", [1,2,3] );		
		$("#tablaCaptura").show()
		$("#tablaLectura").hide()
		$("#tablaFiltro").show()
	}
	else{
		//$( "#tabs" ).tabs( "option", "disabled", [] );
		$("#abrir").show()		
		$("#tablaCaptura").hide()
		$("#tablaLectura").show()
		$("#tablaFiltro").hide()
		
		document.title = $("#nombrePaciente").val() + " Cama: " + $('#cama').html() + 
		" Fecha Elaboracion: " + $("#fechaElaboracion").val()
	}	
	
	$( "#mostrarSeguimientos" ).dialog({
		  title:'Seguimientos Disponibles',
		  position: 'top',
	      autoOpen: false,
	      width:"500px"
	});
	
	$("#fechaElaboracion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	}).attr('readonly', 'readonly');	
	
	if($( "#mensaje" ).html() != ''){	
		$( "#mensaje" ).dialog({
	      modal: true,
	      buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
	}
	
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
				
				//cargarHojaHistorica(json.hoja,json.dietas,json.requisitos)
				
				$("#abrir").show()
				
			})
		.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
		})
	})
	
});

function mostrarSeguimientos(){	
	 
	var idPaciente = $("#idPaciente").val()	 
	
	$.blockUI({ message: '<h1>Cargando seguimientos...</h1>' });
	$.getJSON("/enfermeria/seguimientoHosp/consultarSeguimientos",
			 {idPaciente:idPaciente})
	.done(function( json ) {
		$( "#mostrarSeguimientos" ).html(json.html)				
		tablaFloatHead("#tablaSeguimientos")			
		$( "#mostrarSeguimientos" ).dialog( "open" );						
	})
	.fail(function() {
		mostrarMensaje("Ocurrio un error al mostrar los seguimientos","error")
	})
	.always(function() {
		$.unblockUI();
	})
}

function consultarSeguimiento(idSeguimiento, fechaElaboracion){
	redirectConsultar(idSeguimiento,"Seguimiento Cargado")
}

function redirectConsultar(idSeguimiento,mensaje){
	$("#idSeguimientoR").val(idSeguimiento);	
	$("#mensajeR").val(mensaje);	
	$("#formRedirect").submit();
}

