$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};

function autoComplete(input,url,hidden,funcSelect, minimumTrigger){
	$(input).autocomplete({
		  source: function(request, response){
		   $.ajax({
			type: "GET",
		    url: url,		    
		    dataType: "json",		    
		    data: {
                term : request.term,
                idArea: $( "#pisos option:selected" ).val(),
                idServicio:$( "#servicios option:selected" ).val(),
                historico:$('#historico').is(':checked')
            },		    
		    success: function(data){
		     response(data); // set the response
		    },
		    error: function(e){ // handle server errors
		    	mostrarMensaje("Ocurrio un error al realizar la busqueda","error")
		    }
		   });
		  },
		  minLength: minimumTrigger, // triggered only after minimum 2 characters have been entered.
		  autoFocus: true,
		  select: function(event, ui) { // event handler when user selects a company from the list.
			  $(hidden).val(ui.item.id); // update the hidden field.
			  
			  funcSelect();
		  }
	 });
	
}

function autoCompletePaciente(funcSelect){
	autoComplete("#pacienteauto","/enfermeria/autoComplete/consultarPacientes","#idPaciente",funcSelect,3)
}


function isNumberPointKey(evt)
{
  var keyPressed = (evt.which) ? evt.which : event.keyCode
	return !((keyPressed !=13) && (keyPressed != 46) && (keyPressed != 8) && (keyPressed < 48 || keyPressed > 57));
}


function isNumberKey(evt)
{
  var keyPressed = (evt.which) ? evt.which : event.keyCode
	return !((keyPressed !=13) && (keyPressed != 46) && (keyPressed != 8) && (keyPressed < 48 || keyPressed > 57));
}

function anularBack() {
	$(document).keydown(function(e) {
		var element = e.target.nodeName.toLowerCase();
		if (element != 'input' && element != 'textarea') {
		    if (e.keyCode === 8) {
		        return false;
		    }
		}
	});
}


function redirectConsultarHoja(idHoja,turnoActual,mensaje,nuevaHoja){
	$("#idHojaR").val(idHoja);
	$("#turnoActualR").val(turnoActual);
	$("#mensajeR").val(mensaje);
	$("#nuevaHojaR").val(nuevaHoja);
	
	$("#formRedirect").submit();
}


function guardarTextTabla(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/util/guardarTextTabla",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				//$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				mostrarMensaje("Ocurrio un error al guardar el texto","error")
			})
			
}

function guardarTextTablaConHora(idHoja,idProcedimiento,valor,hora,modificarHora, idMensaje,mensaje){
	
	$.getJSON("/enfermeria/util/guardarTextTablaConHora",{idHoja:idHoja,idProcedimiento:idProcedimiento,
		valor:valor,hora:hora,modificarHora:modificarHora})
		.done(function( json ) {		
				$('#'+idMensaje).html(mensaje)		
		})
		.fail(function() {
			mostrarMensaje("Ocurrio un error al guardar el texto","error")
		})
			
}


function guardarTextTablaSinBorrar(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/util/guardarTextTablaSinBorrar",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				//$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				mostrarMensaje("Ocurrio un error al guardar el texto","error")
			})
			
}


function hojaSoloLectura(){
	
	if($("#idHoja").val() != ''){
		if($("#soloLectura").val() == 'true'){		
			$(".ui-spinner-input").spinner( "destroy");			
			$("input:text").attr('disabled',true)
			$("textarea").attr('disabled',true)
			$("input:radio").attr('disabled',true)
			$("input:checkbox").attr('disabled',true)
			$(".escalaDolorImagen").attr('onclick','');			
			$(".operacion").hide()
			
		}
	}
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
				mostrarMensaje("Ocurrio un error al cargar los servicios","error")
			})
	});
}


function tablaFloatHead(selector){
	
	var $table = $(selector);
	$table.floatThead({
		zIndex: 50,
		scrollContainer: function($table){
				return $table.closest('.wrapper');
		}
	});
	
	$table.trigger('reflow');
	
	
}


function mostrarMensaje(mensaje, status){
	
	var image = ""
	
	if(status != undefined){
		
		switch(status){
		
		case 'ok':
			image = "<img  src='/enfermeria/images/icons/paloma.gif' />"
			break
		case 'error':
			image = "<img  src='/enfermeria/images/icons/error_message.jpg' />"
			break
		
		}
	}
	
	var html = "<p>" + image + mensaje + "</p>"
	
	$( "#dialog-mensaje" ).dialog({
		  title:'Mensaje',
		  autoOpen: false,
	      modal: true,
	      buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      resizable: false
	})
	
	
	
	$("#dialog-mensaje" ).html(html)	       					
	$("#dialog-mensaje" ).dialog("open");	
}


function mostrarConfirmacion(mensaje, functionSi){
	
	$( "#dialog-confirm" ).dialog({
		  title:'Confirmacion',
	      resizable: false,
	      height:200,
	      modal: true,
	      buttons: {
	        "Si": function(){
	        	functionSi(); 
	        	$( this ).dialog( "close" )
	        },
	        "No": function() {
	          $( this ).dialog( "close" );
	        }
	      }
	});
	
	$("#dialog-confirm" ).html(mensaje)	       					
	$("#dialog-confirm" ).dialog("open");	
	
}

function imprimirHoja(idHoja){
	
	mostrarMensaje("Generando el reporte..., para continuar de click en OK","ok")
	location.href='/enfermeria/hojaRegistroClinico/reporteHoja/'+idHoja
	
}

function firmarConEnter(){	
	 $("#passwordFirma").keypress(function(e){	
		  	if(e.which == 13) {
		  		$("#btnFirmarHoja").trigger('click')
		  	}
	  })	
}


