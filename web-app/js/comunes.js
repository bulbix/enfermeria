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
		    	//alert('Error121212: ' + e);
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
	autoComplete("#pacienteauto","/enfermeria/autoComplete/consultarPacientes","#idPaciente",funcSelect,4)
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


function redirectConsultarHoja(idHoja,turnoActual,mensaje){
	$("#idHojaR").val(idHoja);
	$("#turnoActualR").val(turnoActual);
	$("#mensajeR").val(mensaje);
	$("#formRedirect").submit();
}


function guardarTextTabla(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/util/guardarTextTabla",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				//$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				//alert("Ocurrio un error al añadir la escala")
			})
			
}

function guardarTextTablaConHora(idHoja,idProcedimiento,valor,hora,modificarHora, idMensaje,mensaje){
	
	$.getJSON("/enfermeria/util/guardarTextTablaConHora",{idHoja:idHoja,idProcedimiento:idProcedimiento,
		valor:valor,hora:hora,modificarHora:modificarHora})
		.done(function( json ) {		
				$('#'+idMensaje).html(mensaje)		
		})
		.fail(function() {				
		})
			
}


function guardarTextTablaSinBorrar(idHoja,idProcedimiento,valor){
	
	$.getJSON("/enfermeria/util/guardarTextTablaSinBorrar",{idHoja:idHoja,idProcedimiento:idProcedimiento,valor:valor})
		.done(function( json ) {		
				//$("#mensaje").html(json.mensaje)		
			})
			.fail(function() {
				//alert("Ocurrio un error al añadir la escala")
			})
			
}