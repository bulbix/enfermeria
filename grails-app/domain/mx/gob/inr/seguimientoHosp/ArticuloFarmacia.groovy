package mx.gob.inr.seguimientoHosp


class ArticuloFarmacia {
	
	String desArticulo
	String unidad
	String presentacion
	Double movimientoProm
	String almacen
	
	static mapping = {
		id column:'cve_art'
		version false		
		table 'articulo'		
		cache true
	}
	
	
	String toString(){
		return String.format("(%s) %s", id, desArticulo)
	}
	
}