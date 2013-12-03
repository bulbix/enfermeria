$(document).ready(function() {
	
	$( ".accordion" ).accordion({ heightStyle: "content", active: false, collapsible: true });
	$(".costo").currency({ region: 'MXN', thousands: ',', decimal: '.', decimals: 2 })	      
	
})