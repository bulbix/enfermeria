$(document).ready(function() {
	
	$("#addIngreso").click(function(){
		
		var $tableBody = $('#tablaIngresos').find("tbody")
		$trLast = $tableBody.find("tr:last")
		
		var descripcion = $trLast.find("input:text.descripcion").attr("name")
		
		//centinela
		var lastId = parseInt(descripcion.substring('descIngreso'.length))
				
		$trNew = $trLast.clone();
		++lastId
		
		//alert(lastId)
		
		$trNew.find("input:text.descripcion").attr("id","descIngreso"+lastId)
		$trNew.find("input:text.descripcion").val('')
		$trNew.find("input:text.horaInicio").attr("id","horaInicio"+lastId)
		$trNew.find("input:text.horaInicio").val('1')
		$trNew.find("input:text.horaFin").attr("id","horaFin"+lastId)
		$trNew.find("input:text.horaFin").val('1')
		$trNew.find("input:text.cantidadIngreso").attr("id","cantidadIngreso"+lastId)
		$trNew.find("input:text.cantidadIngreso").val('')
		$trNew.find("input:button.agregar").attr("onClick","guardarIngreso("+lastId+")");
		$trNew.find("input:button.mostrar").attr("onClick","mostrarIngreso(("+lastId+")");
		$trNew.find("input:button.cambiar").attr("onClick","cambiarIngreso(("+lastId+")");
		
		$trNew.find("input:text.fxpMatutino").attr("id","fxpMatutino"+lastId)
		$trNew.find("input:text.fxpMatutino").val('0')
		$trNew.find("input:text.fxpVespertino").attr("id","fxpVespertino"+lastId)	
		$trNew.find("input:text.fxpVespertino").val('0')
		$trNew.find("input:text.fxpNocturno").attr("id","fxpNocturno"+lastId)	
		$trNew.find("input:text.fxpNocturno").val('0')
		$trNew.find("input:button.agregarfaltante").attr("onClick","guardarFaltante("+lastId+")"); 

		$trLast.after($trNew);
		
	});
	
	$( "#mostrarRegistros" ).dialog({
	      autoOpen: false,
	      width:"600px",
	      show: {
	        effect: "blind",
	        duration: 1000
	      },
	      hide: {
	        effect: "explode",
	        duration: 1000
	      }
	});
	
	
});



function guardarIngreso(id){	
	
	var descripcion = $("#descIngreso"+id).val()
	var horainicio = $("#horaInicio"+id).val()
	var horafin = $("#horaFin"+id).val()
	var totalingresar = $("#cantidadIngreso"+id).val()
	
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarIngreso",
	{descripcion:descripcion,horainicio:horainicio,horafin:horafin,totalingresar:totalingresar,idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el ingreso")
		})	
}

function guardarFaltante(id){	
	
	var descripcion = $("#descIngreso"+id).val()
	var fxpM= $("#fxpMatutino"+id).val()
	var fxpV = $("#fxpVespertino"+id).val()
	var fxpN = $("#fxpNocturno"+id).val()
	
	
	$.getJSON("/enfermeria/hojaRegistroClinico/guardarFaltante",
	{descripcion:descripcion,fxp:JSON.stringify([fxpM,fxpV,fxpN]),idHoja:$("#idHoja").val()})
	.done(function( json ) {		
			$("#mensaje").html(json.mensaje)			
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el ingreso")
		})	
}

function mostrarIngreso(id){
	
	 var descripcion = $("#descIngreso"+id).val()
	 var idHoja = $("#idHoja").val()
	 
	 $.getJSON("/enfermeria/hojaRegistroClinico/consultarDetalleIngreso",
	 {descripcion:descripcion,idHoja:idHoja,numeroRenglon:id})
	.done(function( json ) {			
			//console.log(json.html)
			$( "#mostrarRegistros" ).html(json.html)			
						
		})
		.fail(function() {
			alert("Ocurrio un error al añadir el ingreso")
		})	
	
	
	 $( "#mostrarRegistros" ).dialog( "open" );
	
}


function borrarDetalleIngreso(idRegistro){
	
	 $.getJSON("/enfermeria/hojaRegistroClinico/borrarDetalleIngreso", {idRegistro:idRegistro})
	 	.done(function( json ) {
	 		$( "#rowIngreso"+idRegistro ).remove()		
								
		})
		.fail(function() {
			
	})
}

function borrarAllDetalleIngreso(id){
	
	 var descripcion = $("#descIngreso"+id).val()
	 var idHoja = $("#idHoja").val()
	
	 $.getJSON("/enfermeria/hojaRegistroClinico/borrarAllDetalleIngreso",  {descripcion:descripcion,idHoja:idHoja})
	 	.done(function( json ) {
	 		var $tableBody = $('#tablaIngreso'+descripcion).find("tbody")
			$trLast = $tableBody.find("tr").remove()
								
		})
		.fail(function() {
			
	})
}


