function filtrarTablas(texto){
	
	$(".tablaEnfermeria th").each(function(i) {
		if($(this).html().trim().toLowerCase().indexOf(texto.toLowerCase()) != -1){
			$(this).parent().parent().parent().parent().show()
		}
		else{
			$(this).parent().parent().parent().parent().hide()
		}
	});		
	
}


function guardarCheckTabla(idHoja,idProcedimiento,turno,valor){
	
	var request = $.ajax({
		type:'POST',		
		url: '/enfermeria/util/guardarCheckTabla',
		async:false,
		data:{
			idHoja:idHoja, 
			idProcedimiento:idProcedimiento,			
			turno:turno,
			valor:valor
		},
		dataType:"json"	        
	}).fail(function(){
		
		mostrarMensaje("Ocurrio un error al guardar el check","error")
		
	})
}


function guardarRadioTabla(idHoja,idProcedimiento,valor){
	
	var request = $.ajax({
		type:'POST',		
		url: '/enfermeria/util/guardarRadioTabla',
		async:false,
		data:{
			idHoja:idHoja, 
			idProcedimiento:idProcedimiento,
			valor:valor
		},
		dataType:"json"	        
	}).fail(function(){		
		mostrarMensaje("Ocurrio un error al guardar la opcion","error")
		
	})
}

function borrarRadioTabla(idHoja,idProcedimiento,radio){
	
	var request = $.ajax({
		type:'POST',		
		url: "/enfermeria/util/borrarRadioTabla",
		async:false,
		data:{
			idHoja:idHoja, 
			idProcedimiento:idProcedimiento
		},
		dataType:"json"	        
	}).fail(function(){
		
		mostrarMensaje("Ocurrio un error al borrar la opcion","error")
		
	})
	
	request.done(function(data) {
		$('input:radio[name="'+radio+'"]').attr("checked",false)		
	});
	
}

function seleccionarChecks(selector, check){	
	
	$("input[type='checkbox'][name="+selector+"]").each(function(i) {		
		$(this).prop('checked', check);		
		$(this).trigger('change')
	});	
	
}

function seleccionarRadios(selector, valor){	
	
	if(valor != ''){ //SI o NO		
		$("input[type='radio']."+selector).each(function(i) {
			$(this).val(valor)		
			$(this).trigger('click')
		});		
	}
	else{//Limpieza
		$("input[type='button'][name="+selector+"]").each(function(i) {					
			$(this).trigger('click')
		});		
	}
	
	
}
