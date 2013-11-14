$(document).ready(function() {
	
	$( ".horaInicio" ).spinner({ min:1, max: 24 })
	$( ".horaFin" ).spinner({ min:1, max: 24 })
	
	$( "#dialog-cambiarDescripcion" ).dialog({		  
	      autoOpen: false,
	      width:"600px",
	      modal: true
	})		
	
	var tablaTextos = $("#tablaIngresos").find(".descripcion")
	autoComplete(tablaTextos, "/enfermeria/controlLiquidosMedicamentos/listarIngresos",null,function(){},4)
	tablaTextos = $("#tablaMedicamentos").find(".descripcion")
	autoComplete(tablaTextos, "/enfermeria/controlLiquidosMedicamentos/listarMedicamentos",null,function(){},4)
	tablaTextos = $("#tablaEscalaOtros").find(".descripcion")
	autoComplete(tablaTextos, "/enfermeria/controlLiquidosMedicamentos/listarEscalaOtros",null,function(){},4)
	
	$("#addIngreso").click(function(){		
		clonarFila('tablaIngresos','Ingreso')		
	});	
	
	$("#addMedicamento").click(function(){
		clonarFila('tablaMedicamentos','Medicamento')	
	});
	
	$("#addEscalaOtro").click(function(){
		clonarFila('tablaEscalaOtros','EscalaOtro')	
	});
	
});

function clonarFila(tabla, tipo){
	
	
	var $tableBody = $('#'+tabla).find("tbody")
	$trLast = $tableBody.find("tr:last")
	
	var descripcion = $trLast.find("input:text.descripcion").attr("id")
	
	//centinela
	var lastId = parseInt(descripcion.substring(('desc'+tipo).length))
			
	$trNew = $trLast.clone();
	++lastId
	
	$trNew.find("input:text.descripcion").attr("id","desc"+tipo+lastId)
	var descripcion = $trNew.find("input:text.descripcion")
	descripcion.val('')
	autoComplete(descripcion, "/enfermeria/controlLiquidosMedicamentos/listar"+tipo+"s",null,function(){},4)
	
	$trNew.find("input:text").attr("readonly",false)
	
	/*Hacemos limpieza de los spinners y los volvemos asignar*/
	$trNew.find("input:text.horaInicio").parent().
	replaceWith('<input type="text" id="horaInicio' + tipo+lastId +'" class="horaInicio" value="1" size="2" onkeypress="return isNumberKey(event)"/>')
	$trNew.find("input:text.horaInicio").spinner({ min:1, max: 24 })

	//$trNew.find("input:text.horaFin").attr("id","horaFin"+tipo+lastId)
	
	$trNew.find("input:text.horaFin").parent().
	replaceWith('<input type="text" id="horaFin' + tipo+lastId +'" class="horaFin" value="1" size="2" onkeypress="return isNumberKey(event)"/>')
	$trNew.find("input:text.horaFin").spinner({ min:1, max: 24 })
	
	

	$trNew.find("input:text.cantidad").attr("id","cantidad"+tipo+lastId)
	$trNew.find("input:text.cantidad").val('')
	$trNew.find("input:button.agregar").attr("onClick","guardar"+tipo+"("+lastId+")");
	$trNew.find("input:button.mostrar").attr("onClick","mostrar"+tipo+"("+lastId+")");
	$trNew.find("input:button.cambiar").attr("onClick","cambiar"+tipo+"("+lastId+")");
	
	$trNew.find("input:text.fxpMatutino").attr("id","fxpMatutino"+lastId)
	$trNew.find("input:text.fxpMatutino").val('0')
	$trNew.find("input:text.fxpVespertino").attr("id","fxpVespertino"+lastId)	
	$trNew.find("input:text.fxpVespertino").val('0')
	$trNew.find("input:text.fxpNocturno").attr("id","fxpNocturno"+lastId)	
	$trNew.find("input:text.fxpNocturno").val('0')
	$trNew.find("input:button.agregarfaltante").attr("onClick","guardarFaltante("+lastId+")"); 

	$trLast.after($trNew);
	
	$trNew.find("input:text.descripcion").focus()	
}


function guardarLiquido(id,tipo,idMensaje){
	
	var idHoja = $("#idHoja").val()
	var descripcion = $("#desc"+tipo+id).val()
	var horainicio =$("#horaInicio"+tipo+id).val()
	var horafin = $("#horaFin"+tipo+id).val()
	var totalingresar = ""	
		
	if(descripcion  == ''){
		mostrarMensaje("No puede agregar un " + tipo + " vacio",'error' )
		return	
	}	
		
	if(horainicio  == ''){
		mostrarMensaje("Hora inicio vacio en " + descripcion,'error' )
		return	
	}
	if(horafin  == ''){
		mostrarMensaje("Hora fin vacio en " + descripcion,'error' )
		return	
	}		
		
		
	horainicio = parseInt(horainicio)
	horafin = parseInt(horafin)
	var mensaje =''
	
	if(tipo=='Ingreso' || tipo=='Egreso'){
		
		/*Valida si existe una hora*/
		if(horainicio > horafin ){			
			for(var hora=horainicio; hora <= 24; hora++){	
				if(existeHoraLiquido(tipo,idHoja,descripcion,hora)){			
					mostrarMensaje("La hora " + hora + " ya tiene registro en " + descripcion,'error' )
					return
				}
			}			
			for(var hora=1; hora <= horafin; hora++){
				if(existeHoraLiquido(tipo,idHoja,descripcion,hora)){			
					mostrarMensaje("La hora " + hora + " ya tiene registro en " + descripcion,'error' )
					return
				}
			}
		}
		else{
			for(var hora=horainicio; hora <= horafin; hora++){		
				if(existeHoraLiquido(tipo,idHoja,descripcion,hora)){			
					mostrarMensaje("La hora " + hora + " ya tiene registro en " + descripcion,'error' )
					return
				}
			}			
		}		
				
		
		mensaje = descripcion + " guardado de la hora " + horainicio + " a la " + horafin		
	}
	else{
		
		if(existeHoraLiquido(tipo,idHoja,descripcion,horainicio)){			
			mostrarMensaje("La hora " + horainicio + " ya tiene registro en " + descripcion,'error' )
			return
		}
		
		mensaje = descripcion + " guardado a la hora " + horainicio
	}
	
	
	if(tipo == 'Egreso'){
		elemento = $("#cantidad"+tipo+id)
		if(elemento.is(":radio")){
			totalingresar = $("#cantidad"+tipo+id+":checked").val()			
		}
		else{
			cuantificar = $("#cuantificar"+tipo+id)
			
			if(cuantificar.is(":checked")){
				totalingresar ="No Presenta"
			}
			else{
				totalingresar = elemento.val()		
			}
			
		}
		
	}
	else{
		totalingresar = $("#cantidad"+tipo+id).val()
	}
	
	
	if(totalingresar == undefined || totalingresar == ''){
		mostrarMensaje("Registro vacio en " + descripcion,'error' )
		return		
	}
	
	
	$.getJSON("/enfermeria/controlLiquidosMedicamentos/guardarLiquido",
	{descripcion:descripcion,horainicio:horainicio,horafin:horafin,
	totalingresar:totalingresar,idHoja:idHoja,tipo:tipo})
	.done(function( json ) {		
			mostrarMensaje(mensaje,'ok')
			$("#desc"+tipo+id).attr('readonly',true)
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el "+ tipo)
		})	
	
	
	
}


function guardarIngreso(id){	
	guardarLiquido(id,'Ingreso','mensajeIngreso')	
}

function guardarEgreso(id){	
	guardarLiquido(id,'Egreso','mensajeEgreso')	
}

function guardarMedicamento(id){
	guardarLiquido(id,'Medicamento','mensajeMedicamento')
}

function guardarEscalaOtro(id){
	guardarLiquido(id,'EscalaOtro','mensajeEscalaOtro')
}

function guardarFaltante(id){	
	
	var descripcion = $("#descIngreso"+id).val()
	var fxpM= $("#fxpMatutino"+id).val()
	var fxpV = $("#fxpVespertino"+id).val()
	var fxpN = $("#fxpNocturno"+id).val()
	
	if(descripcion  == ''){
		mostrarMensaje("No puede agregar un Faltante en un Ingreso vacio",'error')
		return	
	}	
	
	
	$.getJSON("/enfermeria/controlLiquidosMedicamentos/guardarFaltante",
	{descripcion:descripcion,fxp:JSON.stringify([fxpM,fxpV,fxpN]),idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			mostrarMensaje("Faltante por pasar guardado de " + descripcion,"ok")
			$("#descIngreso"+id).attr('readonly',true)
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el faltante")
		})	
}

function mostrarLiquido(id,tipo){
	
	 var descripcion = $("#desc"+tipo+id).val()
	 var idHoja = $("#idHoja").val()
	 
	 
	  $.blockUI({ message: '<h1>Mostrando '+ tipo +'...</h1>' });
	 $.getJSON("/enfermeria/controlLiquidosMedicamentos/consultarDetalleLiquidoHtml",
	 {descripcion:descripcion,idHoja:idHoja,tipo:tipo})
	.done(function( json ) {
			$("#mostrarRegistros" ).html(json.html)
			$("#eliminarMisRegistros" ).bind("click", function(){borrarAllDetalleLiquido(id,tipo)})
			hojaSoloLectura()
			$("#mostrarRegistros").dialog('option', 'title',tipo +': ' + descripcion);
			tablaFloatHead("#tablaLiquido")			
			$("#mostrarRegistros" ).dialog( "open" );
			$.unblockUI()
						
		})
		.fail(function() {
			alert("Ocurrio un error al mostrar el "+tipo )
		})
}

function mostrarIngreso(id){	
	mostrarLiquido(id,"Ingreso")
}

function mostrarEgreso(id){	
	mostrarLiquido(id,"Egreso")
}

function mostrarMedicamento(id){	
	mostrarLiquido(id,"Medicamento")
}

function mostrarEscalaOtro(id){	
	mostrarLiquido(id,"EscalaOtro")
}

function borrarDetalleLiquido(idRegistro){
	
	 $.getJSON("/enfermeria/controlLiquidosMedicamentos/borrarDetalleLiquido", {idRegistro:idRegistro})
	 	.done(function( json ) {
	 		$( "#filaLiquido"+idRegistro ).remove()		
								
		})
		.fail(function() {
			alert("Ocurrio un error al borrar el liquido")
			
	})
}

function borrarAllDetalleLiquido(id, tipo){
	
	var r = confirm("Esta seguro de eliminar sus registros?");
	
	if (r == true) {	
		 var descripcion = $("#desc"+tipo+id).val()
		 var idHoja = $("#idHoja").val()
		
		 $.getJSON("/enfermeria/controlLiquidosMedicamentos/borrarAllDetalleLiquido",
			{idHoja:idHoja, descripcion:descripcion, tipo:tipo})
		 	.done(function( json ) {
		 		mostrarLiquido(id,tipo)	 		
			})
			.fail(function() {
				alert("Ocurrio un error al borrar el liquido")
		})
	}
}

function existeHoraLiquido(tipo, idHoja,descripcion,hora){
	
	var existeHora = false
	
	var request = $.ajax({
		type:'POST',		
		url: '/enfermeria/controlLiquidosMedicamentos/existeHoraLiquido',
		async:false,
		data:{
			idHoja:idHoja, 
			descripcion:descripcion,			
			hora:hora,
			tipo:tipo
		},
		dataType:"json"	        
	});
	
	request.done(function(data) {		
		existeHora = data.existeHora		
	});
	
	return existeHora
	
}

function cambiarLiquido(id,tipo){
	 var descripcion = $("#desc"+tipo+id).val()
	 var idHoja = $("#idHoja").val()
	 $("#descripcionNew").val('')	
	 
	 autoComplete("#descripcionNew", "/enfermeria/controlLiquidosMedicamentos/listar"+tipo+"s",null,function(){},4)
	 
	 
	 $("#btnCambiarDescripcion").unbind("click")
	 
	 
	 $("#btnCambiarDescripcion").bind("click", function(){
		 
		 $.getJSON("/enfermeria/controlLiquidosMedicamentos/cambiarLiquido",
				 {descripcionOld:descripcion,descripcionNew: $("#descripcionNew").val(),idHoja:idHoja,tipo:tipo})
				.done(function( json ) {
						$("#dialog-cambiarDescripcion" ).dialog( "close" );
						//alert("#desc"+tipo+id)
						$("#desc"+tipo+id).val($("#descripcionNew").val())
						$("#dialog-mensaje" ).html("Descripcion Actualizada Correctamente")	       					
						$("#dialog-mensaje" ).dialog( "open" );									
					})
					.fail(function() {
						alert("Ocurrio un error al mostrar el "+tipo )
					})
		 
		 
		 
		 
	 })
	 
	 $("#descripcionOld").html(descripcion)
	 $("#dialog-cambiarDescripcion").dialog('option', 'title',tipo +': ' + descripcion);
	 $("#dialog-cambiarDescripcion" ).dialog( "open" );
		
}

function cambiarIngreso(id){
	cambiarLiquido(id,'Ingreso')
}

function cambiarMedicamento(id){
	cambiarLiquido(id,'Medicamento')
}

function cambiarEscalaOtro(id){
	cambiarLiquido(id,'EscalaOtro')
}
