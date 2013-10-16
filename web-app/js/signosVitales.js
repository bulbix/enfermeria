$(document).ready(function() {
	
	$("#addSignosVitales").click(function(){
		
		var $tableBody = $('#tablaSignosVitales').find("tbody"),
		$trLast = $tableBody.find("tr:last"),
		$trNew = $trLast.clone();

		$trLast.after($trNew);
		
	});
	
	 /*$('#tablaSignosVitales').freezeTable({
	        'autoHeight': true,
	        'autoHeightMarginBottom'    : 20
	 });*/

	
});
