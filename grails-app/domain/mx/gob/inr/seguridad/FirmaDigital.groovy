package mx.gob.inr.seguridad

class FirmaDigital {

   String passwordfirma
   String nombre
   String tipo
   Long tamanio
   byte[] datos
   Integer bloqueada
   Integer intentosfallidos

	static mapping = {
		id column:'idusuario'
		version false
		table 'firmadigital'		
	}
	
	static constraints = {
		datos(nullable:true)
	}
	
	
}
