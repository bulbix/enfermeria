$(document).ready(function() {
	
	$.datepicker.setDefaults($.datepicker.regional['es']);
	
	$( "#tabs" ).tabs({
		beforeActivate: function( event, ui ) {
			$(".mensaje").html('')
		}
	});
	
	if($("#idHoja").val() == ''){
		$( "#tabs" ).tabs( "option", "disabled", [1,2,3,4,5] );		
		$("#tablaCaptura").show()
		$("#tablaLectura").hide()
		$("#tablaFiltro").show()
	}
	else{
		$( "#tabs" ).tabs( "option", "disabled", [] );
		$("#abrir").show()		
		$("#tablaCaptura").hide()
		$("#tablaLectura").show()
		$("#tablaFiltro").hide()		
	}	
	
	$( "#mostrarHojas" ).dialog({
		  title:'Hojas Disponibles',
		  position: 'top',
	      autoOpen: false,
	      width:"900px",
	      modal: true
	});
	
	$( "#mostrarFirma" ).dialog({
		title:'Password de la Firma Electronica',
	    autoOpen: false,
	    width:"600px",
	    modal: true
	});
	
	$( "#mostrarRegistros" ).dialog({		  
	      autoOpen: false,
	      width:"800px",
	      modal: true
	});	
	
	 
	
	$("#fechaElaboracion").datepicker({
		dateFormat: 'dd/mm/yy',
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true		
	}).attr('readonly', 'readonly');	
	
	cargarServicios()
	validar()
	
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
				$("#nombrePaciente").val(json.nombrePaciente)
				
				//cargarHojaHistorica(json.hoja,json.dietas,json.requisitos)
				
				$("#abrir").show()
				
			})
		.fail(function() {
				alert("Ocurrio un error al consultar el paciente")
		})
	})
	
	hojaSoloLectura()
	
	anularBack()
	
});




function cargarHojaHistorica(hoja,dietas,requisitos){
	
	/**Cargamos datos historicos**/
	if(hoja != null){
		alert(hoja.has)
		$("#has").val(hoja.has)
		$("#dm").val(hoja.dm)
		$("#nef").val(hoja.nef)
		$("#ic").val(hoja.ic)
		$("#ir").val(hoja.ir)
		$("#peso").val(hoja.peso)
		$("#talla").val(hoja.talla)
		$("#alergias").val(hoja.alergias)
		$("#otros").val(hoja.otros)
	}
	
	if(dietas != null){
		if(dietas[0] != null)
			$("#dieta").val(dietas[0].otro)
		
		if(dietas[1] != null)
			$("#dietaM").val(dietas[1].otro)
		
		if(dietas[2] != null)
			$("#dietaV").val(dietas[2].otro)
			
		if(dietas[3] != null)
			$("#otrosN").val(dietas[3].otro)
	}
	
	if(requisitos != null){
		if(requisitos[0] != null)
			$("#requisitoDesarrollo").val(requisitos[0].otro)
		if(requisitos[1] != null)
			$("#requisitoSalud").val(requisitos[1].otro)
	}
	
}

function validar(){
	$("#formHojaEnfermeria").validate({
		
		ignore: [],
		
        rules: {
        	fechaElaboracion: {required:true,validateDate:true,dateToday:true},
        	idPaciente:{required:true},
        	peso: {required:true, number:true},
        	talla: {required:true, number:true}
             
        },
		messages: {
			fechaElaboracion:{required:"Requerido"},
			idPaciente:{required:'No ha seleccionado paciente'},
			peso:{required:"Requerido",number:"Numerico"},
			talla:{required:"Requerido",number:"Numerico"}
		}
  });
}

function guardarHojaTurno(){
	
	var frm = $("#formHojaEnfermeria");
    var dataHoja = JSON.stringify(frm.serializeObject());
    var jsonHoja = JSON.parse(dataHoja);
    
    /*if(jsonHoja.idPaciente == ''){
			$("#mensaje").html("No ha seleccionado paciente")
			return
	}*/
    
    
    if($("#formHojaEnfermeria").valid()){
		 $.getJSON("/enfermeria/hojaRegistroClinico/guardarHojaTurno",
		  {dataHoja:dataHoja}).done(function( json ) {					 
					 
			  switch(json.status){
			  case 'firmarHoja':
				  $( "#mostrarFirma" ).html(json.html)
				  $( "#mostrarFirma" ).dialog( "open" );
				  $("#passwordFirma").focus()
				  firmarConEnter()
				  
				  break;
			  case 'existeHoja':

				  if(jsonHoja.idHoja == ''){
					  $("#mensaje").html("La fecha de elaboracion " + 
							  jsonHoja.fechaElaboracion +" ya tiene registro, dale click Abrir y asocie turno")
				  }
				  else{
					  $("#mensaje").html("Alergias Comorbilidad salvado correctamente")
				  }	 			

			  }
					 
		})
		.fail(function() {			
		})
    }
	
}

function mostrarHojas(){	
	 
	 var idPaciente = $("#idPaciente").val()
	 
	 
	 $.getJSON("/enfermeria/hojaRegistroClinico/consultarHojas",
			 {idPaciente:idPaciente})
	.done(function( json ) {
		$( "#mostrarHojas" ).html(json.html)
		$( ".jefe, .supervisor").tooltip()
						
	})
	.fail(function() {
			alert("Ocurrio un error al mostrar las hojas")
	})	
	
	 
	 $( "#mostrarHojas" ).dialog( "open" );	
	
}

function mostrarFirma(idHoja,tieneUsuario,tipoUsuario, fechaElaboracion){
	
	var turnoAsociar = $('#turnoAsociar').val()	
	var usuarioFirma = $('#usuarioFirma')
	
	 $.getJSON("/enfermeria/hojaRegistroClinico/mostrarFirma",
			 {idHoja:idHoja,turnoAsociar:turnoAsociar,
		 	tieneUsuario:tieneUsuario,tipoUsuario:tipoUsuario,fechaElaboracion:fechaElaboracion})
			 .done(function( json ) {
				 
				 switch(json.status){
				 case 'cargarHoja':
					 
					 $( "#mostrarHojas" ).dialog( "close" );	
					 $.blockUI({ message: '<h1>Cargando la hoja...</h1>' });
					 var soloLectura = json.soloLectura				 		

					 if(soloLectura){
						 redirectConsultarHoja(idHoja,turnoAsociar,"Hoja cargada en solo lectura")
					 }
					 else{
						 redirectConsultarHoja(idHoja,turnoAsociar,"Hoja cargada satisfactoriamente")
					 }


					 break
				 case 'firmarHoja':
					 $("#mostrarFirma" ).html(json.html)
					 $("#mostrarFirma").dialog('option', 'title','Firmar '+ tipoUsuario);
					 $("#mostrarFirma" ).dialog( "open" );				 
					  
					 firmarConEnter()
					 
					 autoComplete('#usuarioFirma', "/enfermeria/autoComplete/consultarEnfermeras",'#idUsuarioFirma',
							 function(){},3)	

				 }
				 
	})
	.fail(function() {			
	})
	
}


function firmarConEnter(){
	
	 $("#passwordFirma").keypress(function(e){	
		  	if(e.which == 13) {
		  		$("#btnFirmarHoja").trigger('click')
		  	}
	  })
	
}


function firmarHoja(idHoja){
	
	var turnoAsociar = $('#turnoAsociar').val()	
	var passwordFirma = $('#passwordFirma').val()	
	var idUsuarioFirma = $('#idUsuarioFirma').val()
	var tipoUsuarioFirma = $('#tipoUsuarioFirma').val()		
	
	
	var frm = $("#formHojaEnfermeria");
    var dataHoja = JSON.stringify(frm.serializeObject());
    var jsonHoja = JSON.parse(dataHoja);	
    
    if(turnoAsociar == undefined){
    	turnoAsociar = jsonHoja.turno
    }
    
    var nombrePaciente = $('#nombrePaciente').val()	
    var cama = $('#cama').html()  
    
    $( "#dialog-confirm" ).dialog({
    	  title:'Validar Turno',
	      resizable: false,
	      height:250,
	      width:500,
	      modal: true,
	      buttons: {
	        "Si": function() {
	        	$.getJSON("/enfermeria/hojaRegistroClinico/firmarHoja",
	       			 {idHoja:idHoja,passwordFirma:passwordFirma,
	       				turnoAsociar:turnoAsociar,dataHoja:dataHoja,
	       				idUsuarioFirma:idUsuarioFirma,tipoUsuarioFirma:tipoUsuarioFirma}).
	       			 done(function( json ) {			
	       				 if(json.firmado==true){
	       					 idHoja = json.idHoja
	       					 $( "#mostrarFirma" ).dialog( "close" );
	       					 $( "#mostrarHojas" ).dialog( "close" );
	       					 $.blockUI({ message: '<h1>Cargando la hoja...</h1>' });
	       					 redirectConsultarHoja(idHoja,turnoAsociar,
	       							"Se ha firmado el turno " + turnoAsociar +" correctamente")
	       				 }
	       				 else{
	       					$("#dialog-confirm" ).dialog( "close" );
	       					$("#passwordFirma").focus()
	       					alert('No coincide la firma')	       					
	       				 }
	       				 
			       	})
			       	.fail(function() {
			       		alert('error')
			       	})
	        },
	        "No": function() {
	        	$("#passwordFirma").focus()
	        	$(this).dialog( "close" ); 
	        }
	      }
	});
    
    var mensaje = "Esta seguro de firmar el turno <span style='color:blue'>" + turnoAsociar + "</span> de <span style='color:blue'>" 
    + nombrePaciente + "</span> Cama: <span style='color:blue'>" + cama + "</span>?, POR FAVOR VERIFIQUE!!!"
    $( "#dialog-confirm" ).html(mensaje)
    $( "#dialog-confirm" ).dialog( "open" ); 
	
}


function imprimirHoja(){
	//$.blockUI({ message: '<h1>Generando el reporte...</h1>' });	
	location.href='/enfermeria/hojaRegistroClinico/reporteHoja/'+document.getElementById('idHoja').value
	//$.unblockUI()
}