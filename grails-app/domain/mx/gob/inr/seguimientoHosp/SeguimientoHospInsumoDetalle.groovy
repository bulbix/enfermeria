package mx.gob.inr.seguimientoHosp

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.Turno

class SeguimientoHospInsumoDetalle {
	
	
	SeguimientoHospInsumo seguimientoHospInsumo
	Integer cantidad
	Turno turno
	Usuario usuario
	Date fechaAlta
	String ipAlta	
	
	static mapping = {
		table 'seg_hosp_insumo_detalle'
		version false
		id generator:'identity'
		seguimientoHospInsumo column:'id_seg_hosp_insumo'
	}

    
}
