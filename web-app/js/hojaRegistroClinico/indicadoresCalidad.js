
var procedimientoIndicador =[]


$(document).ready(function() {
	
	procedimientoIndicador[325] = 'Limitacion fisica';
	procedimientoIndicador[326] = 'Estado mental alterado';
	procedimientoIndicador[327] = 'Tratamiento farmacologico que implica riesgo';
	procedimientoIndicador[328] = 'Problemas de idioma o socioculturales';
	procedimientoIndicador[329] = 'Sin factores de riesgo';
	
	$("#fechaInstalacionV").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true,
		onSelect: function() {
			$(this).trigger('keyup')
			$("#diasConsecV").val(diasRespectoFechaActual($(this).val()))
			$("#diasConsecV").trigger('keyup')			
			$("#calibreV").focus()
		}
	}).attr('readonly', 'readonly');
	
	$("#fechaInstalacionS").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true,
		onSelect: function() {
			$(this).trigger('keyup')
			$("#diasConsecS").val(diasRespectoFechaActual($(this).val()))
			$("#diasConsecS").trigger('keyup')
			$("#calibreS").focus()
		}
	}).attr('readonly', 'readonly');
	
	$( ".horaPrevencion" ).spinner()
	correctSpinner($( ".horaPrevencion" ))
	$( ".horaMadox" ).spinner()
	correctSpinner($( ".horaMadox" ),0,5)
	
	
	var turnoActual = $( "#turno option:selected" ).val()
	
	switch(turnoActual){
		case "MATUTINO":			
			$(".matutinoIndicador").attr("disabled",false)
			$(".vespertinoIndicador").attr("disabled",true)
			$(".nocturnoIndicador").attr("disabled",true)
			$('#madoxVespertino').spinner( "disable" );
			$('#madoxNocturno').spinner( "disable" );
			break
		case "VESPERTINO":
			$(".matutinoIndicador").attr("disabled",true)
			$(".vespertinoIndicador").attr("disabled",false)
			$(".nocturnoIndicador").attr("disabled",true)
			$('#madoxMatutino').spinner( "disable" );
			$('#madoxNocturno').spinner( "disable" );
			break
		case "NOCTURNO":
			$(".matutinoIndicador").attr("disabled",true)
			$(".vespertinoIndicador").attr("disabled",true)
			$(".nocturnoIndicador").attr("disabled",false)
			$('#madoxMatutino').spinner( "disable" );
			$('#madoxVespertino').spinner( "disable" );
			break	
	
	}
	
	hojaSoloLectura()
	
	
});


function diasRespectoFechaActual(fechaString){
	var oneDay = 24*60*60*1000;
	
	var endDateArray= fechaString.split("/");
	var date1= new Date(endDateArray[2],(endDateArray[1] - 1 ), endDateArray[0] ,0,0,0,0);
	
	
	var exactDate= new Date();
    var year= exactDate.getFullYear();
    var month= exactDate.getMonth();
    var day= exactDate.getDate();
    var date2= new Date(year,month,day,0,0,0,0);	

	var diffDays = Math.round((date2.getTime() - date1.getTime())/(oneDay));
	
	return diffDays	
	
}

function guardarPrevencion(id){
	
	
	
	var hora = $("#horaPrevencion"+id).val()
	var idHoja = $("#idHoja").val()
	
	if(hora  == ''){
		mostrarMensaje("Hora vacio", 'error' )
		return	
	}
			
	if(existeHoraPrevencion(idHoja,id,hora)){			
		mostrarMensaje("La hora " + hora + " ya tiene registro en " + procedimientoIndicador[id],'error' )
		return	
	}		
	
	$.getJSON("/enfermeria/indicadoresCalidad/guardarPrevencion",{idProcedimiento:id,hora:hora,idHoja:$("#idHoja").val()})
	.done(function( json ) {		
		mostrarMensaje(procedimientoIndicador[id] + " guardado con la hora " + hora,"ok" )			
	})
	.fail(function() {
		mostrarMensaje("Ocurrio un error al guardar la prevencion","error")
	})		
}

function mostrarPrevencion(id){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.blockUI({ message: '<h1>Mostrando Prevencion...</h1>' });
	 $.getJSON("/enfermeria/indicadoresCalidad/consultarDetallePrevencion",
	 {idHoja:idHoja,idProcedimiento:id})
	 .done(function( json ) {
			$( "#mostrarRegistros" ).html(json.html)
			$("#eliminarMisRegistros" ).bind("click", function(){borrarAllDetallePrevencion(id)})
			hojaSoloLectura()
			$("#mostrarRegistros").dialog('option', 'title','Prevencion de Caidas: '+procedimientoIndicador[id]);			
			tablaFloatHead("#tablaLiquido")							
			$( "#mostrarRegistros" ).dialog( "open" );
			$.unblockUI()
		})
		.fail(function() {
			mostrarMensaje("Ocurrio un error al mostrar la prevencion","error")
		})	
	
	
	
	
}


function existeHoraPrevencion(idHoja,id,hora){
	
	var existeHora = false
	
	var request = $.ajax({
		type:'POST',		
		url: '/enfermeria/indicadoresCalidad/existeHoraPrevencion',
		async:false,
		data:{
			idHoja:idHoja, 
			idProcedimiento:id,			
			hora:hora
		},
		dataType:"json"	        
	});
	
	request.done(function(data) {		
		existeHora = data.existeHora		
	});
	
	return existeHora
	
}


function borrarAllDetallePrevencion(id){
	
	mostrarConfirmacion("Esta seguro de eliminar sus registros?", function(){
		
		var idHoja = $("#idHoja").val()
		
		 $.getJSON("/enfermeria/indicadoresCalidad/borrarAllDetallePrevencion",
			{idHoja:idHoja, idProcedimiento:id})
		 	.done(function( json ) {
		 		mostrarPrevencion(id)	 		
			})
			.fail(function() {
				mostrarMensaje("Ocurrio un error al borrar la prevencion","error")
			})
	})
	
}
