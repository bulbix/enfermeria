
procedimiento =[]
procedimiento[325] = 'Limitacion fisica';
procedimiento[326] = 'Estado mental alterado';
procedimiento[327] = 'Tratamiento farmacologico que implica riesgo';
procedimiento[328] = 'Problemas de idioma o socioculturales';
procedimiento[329] = 'Sin factores de riesgo';

$(document).ready(function() {
	
	$("#fechaInstalacionV").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true,
		onSelect: function() {		
			$("#diasConsecV").val(diasRespectoFechaActual($(this).val()))
			$("#diasConsecV").trigger('blur')
		}
	}).attr('readonly', 'readonly');
	
	$("#fechaInstalacionS").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true,
		onSelect: function() {
			$("#diasConsecS").val(diasRespectoFechaActual($(this).val()))
			$("#diasConsecS").trigger('blur')
		}
	}).attr('readonly', 'readonly');
	
	$( ".horaPrevencion" ).spinner({ min:1, max: 24 })
	$( ".horaMadox" ).spinner({ min:0, max: 5 })
	
	
	var turnoActual = $( "#turno option:selected" ).val()
	
	switch(turnoActual){
		case "MATUTINO":			
			$(".matutinoIndicador").attr("disabled",false)
			$(".vespertinoIndicador").attr("disabled",true)
			$(".nocturnoIndicador").attr("disabled",true)
			break
		case "VESPERTINO":
			$(".matutinoIndicador").attr("disabled",true)
			$(".vespertinoIndicador").attr("disabled",false)
			$(".nocturnoIndicador").attr("disabled",true)
			break
		case "NOCTURNO":
			$(".matutinoIndicador").attr("disabled",true)
			$(".vespertinoIndicador").attr("disabled",true)
			$(".nocturnoIndicador").attr("disabled",false)
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
	
	//alert(date2)
	var diffDays = Math.round((date2.getTime() - date1.getTime())/(oneDay));
	
	return diffDays	
	
}

function guardarPrevencion(id){
	
	
	
	var hora = $("#horaPrevencion"+id).val()
	var idHoja = $("#idHoja").val()
			
	if(existeHoraPrevencion(idHoja,id,hora)){			
		$("#mensajePrevencion").html("La hora " + hora + " ya tiene registro en " + procedimiento[id] )
		return	
	}		
	
	$.getJSON("/enfermeria/indicadoresCalidad/guardarPrevencion",{idProcedimiento:id,hora:hora,idHoja:$("#idHoja").val()})
	.done(function( json ) {		
		$("#mensajePrevencion").html(procedimiento[id] + " guardado a la hora " + hora )			
	})
	.fail(function() {
		alert("Ocurrio un error al añadir el "+ id)
	})		
}

function mostrarPrevencion(id){	
	
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/indicadoresCalidad/consultarDetallePrevencion",
	 {idHoja:idHoja,idProcedimiento:id})
	 .done(function( json ) {
			$( "#mostrarRegistros" ).html(json.html)
			$("#eliminarMisRegistros" ).bind("click", function(){borrarAllDetallePrevencion(id)})
			hojaSoloLectura()
			$("#mostrarRegistros").dialog('option', 'title','Prevencion de Caidas: '+procedimiento[id]);
			$( "#mostrarRegistros" ).dialog( "open" );
		})
		.fail(function() {
			alert("Ocurrio un error al mostrar el "+id )
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
	
	 var idHoja = $("#idHoja").val()
	
	 $.getJSON("/enfermeria/indicadoresCalidad/borrarAllDetallePrevencion",
		{idHoja:idHoja, idProcedimiento:id})
	 	.done(function( json ) {
	 		mostrarPrevencion(id)	 		
		})
		.fail(function() {
			
	})
}
