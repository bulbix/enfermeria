$(document).ready(function() {	
	
	$( "#tabs" ).tabs({
		activate: function( event, ui ) {
			importeGlobal()
		}				
	});	
	
	if($("#idSeguimiento").val() == ''){
		$( "#tabs" ).tabs( "option", "disabled", [1,2,3] );		
		$("#tablaCaptura").show()
		$("#tablaLectura").hide()
		$("#tablaFiltro").show()
		$("#imprimir").hide()
	}
	else{
		$( "#tabs" ).tabs( "option", "disabled", [] );
		$("#abrir").show()		
		$("#tablaCaptura").hide()
		$("#tablaLectura").show()
		$("#tablaFiltro").hide()
		$("#imprimir").show()
		
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
				
				$("#abrir").show()
				$("#btnHistoricoMedicamento").show()
				
			})
		.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
		})
	})
	
	soloLectura()
	
	
	
});

function soloLectura(){
	
	if($("#idSeguimiento").val() != ''){
		if($("#soloLectura").val() == 'true'){						
			$("input:text").attr('disabled',true)
			$("textarea").attr('disabled',true)
			$("input:radio").attr('disabled',true)
			$("input:checkbox").attr('disabled',true)		
		}
	}
}

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


function importeGlobal(){
	
	var importeMedicamentos  = parseFloat($("#importeTotalMedicamento").val().replace('$','').replace(',',''))
	var importeEstudios  = parseFloat($("#importeTotalEstudio").val().replace('$','').replace(',',''))
	var importeCirugias = parseFloat($("#importeTotalCirugia").val().replace('$','').replace(',',''))
	var importeTerapias = parseFloat($("#importeTotalTerapia").val().replace('$','').replace(',',''))
	
	console.debug(importeMedicamentos)
	console.debug(importeEstudios)
	console.debug(importeCirugias)
	console.debug(importeTerapias)
	
	var result = importeMedicamentos + importeEstudios + importeCirugias + importeTerapias
	
	$("#importeGlobal").val(result)
	$("#importeGlobal").currency({ region: 'MXN', thousands: ',', decimal: '.', decimals: 2 })
	
}

function reporteDiario(idSeguimiento){
	
	mostrarMensaje("Generando el reporte..., espere que se genere el reporte y de click en OK","ok")
	location.href='/enfermeria/seguimientoHosp/reporteDiario/'+idSeguimiento
	
}

function reporteEstancia(idPaciente){
	
	mostrarMensaje("Generando el reporte..., espere que se genere el reporte y de click en OK","ok")
	location.href='/enfermeria/seguimientoHosp/reporteEstancia/'+idPaciente
	
}
