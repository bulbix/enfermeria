package mx.gob.inr.seguimientoHosp

import java.util.Date;

class CierreFarmacia implements Serializable {

	ArticuloFarmacia articulo	
	Date fechaCierre
	String almacen
	Integer existencia
	Double importe   	
	
	static mapping = {		
		version false
		table 'cierre'
		articulo column:'cve_art'
		id composite: ['fechaCierre','articulo']
		fechaCierre(type:'date')
	}
	
}
