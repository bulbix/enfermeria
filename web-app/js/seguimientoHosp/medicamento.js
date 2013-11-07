$(document).ready(function() {
	
	autoCompleteArticulo(function(){
		$("#cantidad").focus()
	});
	
	consultarDetalle()
	detalleAdd()
});


function detalleAdd(){
	
	$("#insumo").focus(function(){	
		limpiarRenglonDetalle()
	});
	
	$("#insumo").keypress(function(e){	
		 if(e.which == 13 && $("#insumo").valid() ) {
			$.getJSON("/enfermeria/medicamento/buscarArticulo",{id:this.value})
					.done(function( json ) {
						 $("#artauto").val(json.desArticulo)						 
						 $("#unidad").val(json.unidad)
						 $("#costo").val(json.movimientoProm)				 
						 $("#costo").currency({ region: 'MXN', thousands: ',', decimal: '.', decimals: 4 })
						 $("#cantidad").focus()
						 
					})
					.fail(function() {
						alert("La clave " + $("#insumo").val() + " no existe")
						limpiarRenglonDetalle()
					})
		 }
	});
	
	
	$("#cantidad").keypress(function(e){	
		 if(e.which == 13) {
			 
			 if($("#formPadre").valid()){			 
				 var data = [{ cveArt:$("#insumo").val(),
					 		   cantidad:$("#cantidad").val()
					 		}];
				 
				 guardar(JSON.stringify(data))			
				 
				 $("#clavelast").html($("#insumo").val());
				 $("#deslast").html($("#artauto").val());
				 $("#unidadlast").html($("#unidad").val());
				 $("#costolast").html($("#costo").val());				
				 $("#solicitadolast").html($("#solicitado").val());
				 $("#surtidolast").html($("#surtido").val());
				 $('#disponiblelast').html(disponibilidadArticulo($("#insumo").val(),$("#fecha").val()))
				 
				 limpiarRenglonDetalle()
				 $("#insumo").focus()
			 }
		 }
	});	
}

function autoCompleteArticulo(funcSelect){	
	autoComplete("#artauto", "/enfermeria/medicamento/listarArticulo","#insumo",
			function(){
		  		$.getJSON("/enfermeria/medicamento/buscarArticulo",{id:$("#insumo").val()})
		  		.done(function( json ) {
		  	     $("#desArticulo").val(json.desArticulo)
				 $("#unidad").val(json.unidad)				 
				  $("#costo").val(json.movimientoProm)
				  $("#costo").currency({ region: 'MXN', thousands: ',', decimal: '.', decimals: 4 })
				 funcSelect()
		  	})	
	},4)	
}


function consultarDetalle(){
	
	$("#detalle").jqGrid({
	    url: '/enfermeria/medicamento/consultarDetalle',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Clave','Descripcion', 'U. Medida','Costo','Cantidad'],
	    colModel :[ 
	      {name:'cveArt', index:'cveArt', width:50, align:'center',editable:false}, 
	      {name:'desArticulo', index:'desArticulo', width:500,editable:false},	      
	      {name:'unidad', index:'unidad', width:100,editable:false,align:'center'},
	      {name:'costo', index:'costo', width:120,editable:false,align:'right',formatter: 'currency', formatoptions: { decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 4, prefix: "$", suffix:"", defaultValue: '0.00'}},
	      {name:'cantidad', index:'cantidad', width:90,editable:true,align:'center'}
	    ],
	    postData:{idSeguimiento: function() { return $('#idSeguimiento').val() }},
	    onSelectRow: function(id){	
		},		
		afterInsertRow:function (rowid,rowdata,	rowelem){
		},
	    pager: '#pager',
	    editurl: "/enfermeria/medicamento/actualizarDetalle",
	    rowNum:20,
	    rowList:[20, 40, 60 ,80],
	    //sortname: 'id',
	    //sortorder: 'asc',
	    sortable: false,
	    viewrecords: true,
	    gridview: true,
	    caption: 'Medicamentos'//,
	    //multiselect: true
	})	
	
	$("#detalle").jqGrid('setGridWidth', 850);
	$("#detalle").jqGrid('setGridHeight', 250);
	
	$("#detalle").jqGrid("navGrid", "#pager",
	{
		add: false,
		edit: false,
		del: false,
		search: true,
		refresh: false
	},	
	{//edit
	},
	{},//add
	{//del
		savekey: [true, 13],
		width:500,
		closeOnEscape: true,
		reloadAfterSubmit:true,
		closeAfterSubmit:true,
		afterSubmit: function (data) {
			alert('prueb')
			return [true,'',''];
		}
	},	
	{},
	{});
	
    //$('#detalle').jqGrid('inlineNav',"#pager");
	
}


function limpiarRenglonDetalle(){
	
	$(".busqueda :input").each(function(){
		$(this).val('');
	});
	
}


