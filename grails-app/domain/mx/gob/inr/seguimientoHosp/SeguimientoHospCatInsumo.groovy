package mx.gob.inr.seguimientoHosp

import mx.gob.inr.utils.TipoInsumo

class SeguimientoHospCatInsumo {

	Integer cveArt
	String descripcion
	String unidad
	TipoInsumo tipo
	
    static mapping = {
		table 'seg_hosp_cat_insumo'
		version false		
		id generator:'identity'
		cache usage:'read-only'
	}
}
