$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};

function autoComplete(input,url,hidden,funcSelect, minimumTrigger){
	$(input).autocomplete({
		  source: function(request, response){
		   $.ajax({
			type: "GET",
		    url: url,		    
		    dataType: "json",		    
		    data: {
                term : request.term,
                idArea: $( "#pisos option:selected" ).val(),
                idServicio:$( "#servicios option:selected" ).val(),
                historico:$('#historico').is(':checked')
            },		    
		    success: function(data){
		     response(data); // set the response
		    },
		    error: function(e){ // handle server errors
		    	//alert('Error121212: ' + e);
		    }
		   });
		  },
		  minLength: minimumTrigger, // triggered only after minimum 2 characters have been entered.
		  autoFocus: true,
		  select: function(event, ui) { // event handler when user selects a company from the list.
			  $(hidden).val(ui.item.id); // update the hidden field.
			  
			  funcSelect();
		  }
	 });
	
}

function autoCompletePaciente(funcSelect){
	autoComplete("#pacienteauto","/enfermeria/autoComplete/consultarPacientes","#idPaciente",funcSelect,4)
}
