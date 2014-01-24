$(document).ready(function() {
	
	$( "#accordion" ).accordion({ heightStyle: "content", active: false, collapsible: true });
	
	autoCompletePaciente(function(){})
	
	
	$("#btnMatutinoHistorico").click(function(){
		mostrarHojas($('#idPaciente').val(),'MATUTINO',$('#pacienteauto').val())
	})
	
	$("#btnVespertinoHistorico").click(function(){
		mostrarHojas($('#idPaciente').val(),'VESPERTINO',$('#pacienteauto').val())
	})
	
	$("#btnNocturnoHistorico").click(function(){
		mostrarHojas($('#idPaciente').val(),'NOCTURNO',$('#pacienteauto').val())		
	})
	
	$("#btnNuevoHistorico").click(function(){
		$('#pacienteauto').val('')		
		$('#idPaciente').val('')
		$('#pacienteauto').focus()
	})


	
})



function mostrarHojas(idPaciente, turno, pacienteLabel, mensaje){
	
	//Variables de despliegue
	$("#idPacienteSelect").val(idPaciente)
	$("#pacienteLabelSelect").val(pacienteLabel)
	$("#turnoSelect").val(turno)
	
	
	if(idPaciente == undefined || idPaciente == ''){
		mostrarMensaje("No ha seleccionado paciente","error")
	}
	
	
	$( "#mostrarHojas" ).dialog({
		  title: turno + ": "+ pacienteLabel,
		  position: 'top',
	      autoOpen: false,
	      width:"900px",
	      modal: true,
	      resizable: false
	});	
	
	$.blockUI({ message: '<h1>Cargando hojas...</h1>' });
	$.getJSON("/enfermeria/jefeSupervisor/consultarHojas", {idPaciente:idPaciente, turno:turno})
	.done(function( json ) {
		$( "#mostrarHojas" ).html(json.html)
		$( ".jefe, .supervisor").tooltip()		
		tablaFloatHead("#tablaHojas")			
		$( "#mostrarHojas" ).dialog( "open" );
		
		if(mensaje != undefined){
			mostrarMensaje(mensaje ,"ok")	
			
		}
	})
	.fail(function() {
		mostrarMensaje("Ocurrio un error al mostrar las hojas","error")
	})
	.always(function() {
		$.unblockUI();
	})	
	
}

function mostrarFirma(idHoja, turnoAsociar, tipoUsuario, fechaElaboracion){
	
	$( "#mostrarFirma" ).dialog({
		title:'Password de la Firma Electronica',
	    autoOpen: false,
	    width:"600px",
	    modal: true,
	    resizable: false
	});
	
	 $.blockUI({ message: '<h1>Mostrando firma...</h1>' });
	
	 $.getJSON("/enfermeria/jefeSupervisor/mostrarFirma",
			 {idHoja:idHoja,turnoAsociar:turnoAsociar,tipoUsuario:tipoUsuario,fechaElaboracion:fechaElaboracion})
			 .done(function( json ) {		 
									 
					 $("#mostrarFirma" ).html(json.html)
					 $("#mostrarFirma").dialog('option', 'title','Firmar '+ tipoUsuario);
					 $("#mostrarFirma" ).dialog( "open" );				  
					 firmarConEnter()
					 $.unblockUI();
	})
	.fail(function() {
		mostrarMensaje("Ocurrio un error al mostrar la firma","error")
	})	
}

function firmarHoja(idHoja, turnoAsociar, tipoUsuario, fechaElaboracion){		 	
		 var passwordFirma = $('#passwordFirma').val()

		 $( "#dialog-confirm" ).dialog({
			 title:'Validar Turno',
			 resizable: false,
			 height:250,
			 width:500,
			 modal: true,
			 resizable: false,
			 buttons: {
				 "Si": function() {
					 $.blockUI({ message: '<h1>Firmando Hoja...</h1>' });
					 $.getJSON("/enfermeria/jefeSupervisor/firmarHoja",
							 {idHoja:idHoja,passwordFirma:passwordFirma,turnoAsociar:turnoAsociar,tipoUsuario:tipoUsuario}).
						 done(function( json ) {			
							 if(json.firmado==true){								 
								 $( "#mostrarFirma" ).dialog( "close" );
								 var mensaje = "Turno " + turnoAsociar + " firmado correctamente"
								 mostrarHojas($("#idPacienteSelect").val(), turnoAsociar, $("#pacienteLabelSelect").val(),mensaje)							
							 }
							 else{								 
								 $("#passwordFirma").focus()
								 mostrarMensaje("No coincide el password de la firma, por favor revise","error")								
							 }
							 $("#dialog-confirm").dialog( "close" ); 

						 })
						 .fail(function() {
							 mostrarMensaje('Ocurrio un error al firmar la hoja',"errror")
						 })
				      $.unblockUI();		
						 
						
				 },
				 "No": function() {
					 $("#passwordFirma").focus()
					 $(this).dialog( "close" ); 
				 }
			 }
		 });

		 var mensaje = "Esta seguro de firmar el turno <span style='color:blue'>" + turnoAsociar + "</span> como <span style='color:blue'>"+ tipoUsuario
		 + "</span> Fecha: <span style='color:blue'>" + fechaElaboracion   +"</span>?, POR FAVOR VERIFIQUE!!!"
		 $( "#dialog-confirm" ).html(mensaje)
		 $( "#dialog-confirm" ).dialog( "open" );
}

function consultarHoja(idHoja, turno){	
	nuevaVentana('/enfermeria/hojaRegistroClinico/consultarHoja?idHoja='
	+idHoja+"&turnoActual=" + turno + "&mensaje=Hoja para edicion Jefe/Supervisor&nuevaHoja=false")
	
}


function eliminarHoja(idHoja){	
	
	mostrarConfirmacion("Esta seguro de eliminar la hoja?, POR FAVOR VERIFIQUE!!!", function(){		
		 $.getJSON("/enfermeria/hojaRegistroClinico/eliminarHoja",{idHoja:idHoja}).
		 done(function( json ) {
			 mostrarHojas($("#idPacienteSelect").val(),$("#turnoSelect").val(),
					 $("#pacienteLabelSelect").val(), "Hoja eliminada satisfactoriamente")	 		
		 })
		.fail(function() {
			mostrarMensaje("Ocurrio un error al borrar la hoja","error")
		})		
	})
	
}

