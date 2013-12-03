package mx.gob.inr.seguimientoHosp.terapias

class CatalogoActividadOcupacional {

   String clave
   String descripcion	

   static mapping = {
		table 'catactividadocupacional'
		version false
		id column:'idactividad'		
		cache usage:'read-only'
	}
}
