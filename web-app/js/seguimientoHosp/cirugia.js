$(document).ready(function() {	
	
	$(".costoCirugia").numericInput({ allowFloat: true });	      
	
})



function guardarCostoCirugia(idSeguimiento,idNota,idDiagnostico,costo){
	
	if(costo == ''){
		costo = 0.0
	}
		
	
	var request = $.ajax({
		type:'POST',		
		url: '/enfermeria/cirugia/guardarCostoCirugia',
		async:false,
		data:{
			idSeguimiento:idSeguimiento, 
			idNota:idNota,
			idDiagnostico:idDiagnostico,			
			costo:costo
		},
		dataType:"json"	        
	}).done(function(json){
		
		$("#importeTotalCirugia").val(json.importeTotal)
		$("#importeTotalCirugia").currency({ region: 'MXN', thousands: ',', decimal: '.', decimals: 2 })
		importeGlobal()	
		
	})	
	.fail(function(){
		
		mostrarMensaje("Ocurrio un error al guardar el costo de la cirugia","error")
		
	})
	
}


