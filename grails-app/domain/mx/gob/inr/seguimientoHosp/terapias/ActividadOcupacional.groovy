package mx.gob.inr.seguimientoHosp.terapias

class ActividadOcupacional implements Serializable {

   CatalogoActividadOcupacional catalogo
   Integer idnota   	
	
   static mapping = {		
		version false
		table 'actividadocupacional'		
		//nota column:'idnota'
		catalogo column:'idactividad'
		id composite: ['catalogo','idnota']				
	}
   
   
   String toString(){
	   return  catalogo?.descripcion.trim()
   }
}
