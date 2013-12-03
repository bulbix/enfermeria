package mx.gob.inr.seguimientoHosp.terapias

import mx.gob.inr.utils.NotaMedica;

class SolicitudTerapiaFisica extends NotaMedica {
	
	static hasMany = [detalle:ModalidadTerapiaFisica]
	
	static fetchMode = [detalle: 'eager']
	
    static mapping = {
		table 'solterapiafisica'	
		id column:'idnota'
		paciente column:'idpaciente'
		servicio column:'idservicio'
		usuario column:'idusuario'
		version false
	}
}
