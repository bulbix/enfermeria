package mx.gob.inr.seguimientoHosp.terapias

class ModalidadTerapiaFisica implements Serializable {

   CatalogoModalidad catalogo
   Integer idnota  	
	
   static mapping = {		
		version false
		table 'modalidadterapiafisica'
		catalogo column:'idmodalidad'
		id composite: ['catalogo','idnota']
				
   }
   
   
   
   String toString(){
	   return  catalogo?.descripcion.trim()
   }
   
   
}
