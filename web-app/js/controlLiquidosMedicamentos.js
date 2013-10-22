$(document).ready(function() {
	
	$("#addIngreso").click(function(){
		
		var $tableBody = $('#tablaIngresos').find("tbody")
		$trLast = $tableBody.find("tr:last")
		
		var descripcion = $trLast.find("input:text.descripcion").attr("name")
		
		//alert(descripcion.substring('descIngreso'.length))
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
		$trNew.find("input:text.fxpMatutino").attr("id","fxpMatutino"+lastId)
		$trNew.find("input:text.fxpMatutino").val('0')
		$trNew.find("input:text.fxpVespertino").attr("id","fxpVespertino"+lastId)	
		$trNew.find("input:text.fxpVespertino").val('0')
		$trNew.find("input:text.fxpNocturno").attr("id","fxpNocturno"+lastId)	
		$trNew.find("input:text.fxpNocturno").val('0')
		$trNew.find("input:button.agregarfaltante").attr("onClick","guardarFaltante("+lastId+")"); 

		$trLast.after($trNew);
		
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