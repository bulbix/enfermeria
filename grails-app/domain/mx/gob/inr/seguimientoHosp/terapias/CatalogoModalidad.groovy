package mx.gob.inr.seguimientoHosp.terapias

class CatalogoModalidad {
	
	String clave
	String descripcion	

   static mapping = {
		table 'catalogomodalidad'
		version false
		id column:'idmodalidad'		
		cache usage:'read-only'
	}
}
