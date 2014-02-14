package mx.gob.inr.seguimientoHosp

import java.util.Date;

class SeguimientoHospInsumo {

	SeguimientoHosp seguimientoHosp
	SeguimientoHospCatInsumo insumo
	
    static mapping = {
		table 'seg_hosp_insumo'
		version false		
		id generator:'identity'
		seguimientoHosp column:'id_seg_hosp'
		insumo column:'id_seg_insumo'
	}
}
