$(document).ready(function() {
	
	$( ".horaInicio" ).spinner({ min:1, max: 24 })
	$( ".horaFin" ).spinner({ min:1, max: 24 })
		
	
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
	
	$trNew.find("input:text.horaInicio").attr("id","horaInicio"+tipo+lastId)
	$trNew.find("input:text.horaInicio").val('1')
	$trNew.find("input:text.horaFin").attr("id","horaFin"+tipo+lastId)
	$trNew.find("input:text.horaFin").val('1')
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
	
}


function guardarLiquido(id,tipo){
	
	var descripcion = $("#desc"+tipo+id).val()
	var horainicio = $("#horaInicio"+tipo+id).val()
	var horafin = $("#horaFin"+tipo+id).val()
	var totalingresar = ""
	
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
	
	
	$.getJSON("/enfermeria/controlLiquidosMedicamentos/guardar"+tipo,
	{descripcion:descripcion,horainicio:horainicio,horafin:horafin,totalingresar:totalingresar,idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el "+ tipo)
		})	
	
	
	
}


function guardarIngreso(id){	
	guardarLiquido(id,'Ingreso')
	
}

function guardarEgreso(id){	
	guardarLiquido(id,'Egreso')
	
}

function guardarMedicamento(id){
	guardarLiquido(id,'Medicamento')
}

function guardarEscalaOtro(id){
	guardarLiquido(id,'EscalaOtro')
}

function guardarFaltante(id){	
	
	var descripcion = $("#descIngreso"+id).val()
	var fxpM= $("#fxpMatutino"+id).val()
	var fxpV = $("#fxpVespertino"+id).val()
	var fxpN = $("#fxpNocturno"+id).val()
	
	
	$.getJSON("/enfermeria/controlLiquidosMedicamentos/guardarFaltante",
	{descripcion:descripcion,fxp:JSON.stringify([fxpM,fxpV,fxpN]),idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el ingreso")
		})	
}

function mostrarLiquido(id,tipo){
	
	 var descripcion = $("#desc"+tipo+id).val()
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/controlLiquidosMedicamentos/consultarDetalle"+tipo,
	 {descripcion:descripcion,idHoja:idHoja,numeroRenglon:id})
	.done(function( json ) {
			$( "#mostrarRegistros" ).html(json.html)			
						
		})
		.fail(function() {
			alert("Ocurrio un error al mostrar el "+tipo )
		})	
	
	
	 $( "#mostrarRegistros" ).dialog( "open" );
	
	
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
	 		$( "#rowIngreso"+idRegistro ).remove()		
								
		})
		.fail(function() {
			
	})
}

function borrarAllDetalleIngreso(id){
	
	 var descripcion = $("#descIngreso"+id).val()
	 var idHoja = $("#idHoja").val()
	
	 $.getJSON("/enfermeria/controlLiquidosMedicamentos/borrarAllDetalleIngreso",  {descripcion:descripcion,idHoja:idHoja})
	 	.done(function( json ) {
	 		var $tableBody = $('#tablaIngreso'+descripcion).find("tbody")
			$trLast = $tableBody.find("tr").remove()
								
		})
		.fail(function() {
			
	})
}


