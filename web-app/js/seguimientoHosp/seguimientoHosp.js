$(document).ready(function() {
	
	$.datepicker.setDefaults($.datepicker.regional['es']);
	
	$( "#tabs" ).tabs({});
	
	$( "#mostrarSeguimientos" ).dialog({
		  title:'Seguimientos Disponibles',
		  position: 'top',
	      autoOpen: false,
	      width:"900px"
	});
	
	$("#fechaElaboracion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	}).attr('readonly', 'readonly');	
	
	cargarServicios()
	
	autoCompletePaciente(function(){
		
		var idPaciente = $("#idPaciente").val()
		
		$.getJSON("/enfermeria/autoComplete/consultarDatosPaciente",{idPaciente:idPaciente})
		.done(function( json ) {			
			
				$("#idAdmision").val(json.idAdmision)
				$("#edad").html(json.edad)
				$("#sexo").html(json.sexo)
				$("#religion").html(json.religion)
				$("#cama").html(json.cama)
				$("#diasHosp").html(json.diasHosp)
				$("#servicio").html(json.servicio)
				$("#diagnostico").html(json.diagnostico)
				$("#peso").val(json.peso)
				$("#talla").val(json.talla)
				
				//cargarHojaHistorica(json.hoja,json.dietas,json.requisitos)
				
				$("#abrir").show()
				
			})
		.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
		})
	})
	
});
