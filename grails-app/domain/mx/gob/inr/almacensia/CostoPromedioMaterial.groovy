package mx.gob.inr.almacensia

class CostoPromedioMaterial implements Serializable {

    Integer cveArt
	String almacen
	Double costoPromedio	
	
	static mapping = {		
		table 'costo_promedio'		
		almacen column:'id_almacen'
		id composite: ['cveArt','almacen']
		version false
		cache true
		datasource 'materiales'
	}
}
