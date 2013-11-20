package mx.gob.inr.seguimientoHosp


class ArticuloFarmacia {
	
	String desArticulo
	String unidad
	String presentacion
	Double movimientoProm
	String almacen
	
	Double precioCierre
	
	static transients = ['precioCierre']
	
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