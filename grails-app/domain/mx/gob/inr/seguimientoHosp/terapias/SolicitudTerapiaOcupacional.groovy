package mx.gob.inr.seguimientoHosp.terapias

import mx.gob.inr.utils.NotaMedica

class SolicitudTerapiaOcupacional extends NotaMedica {
	
   static hasMany = [detalle:ActividadOcupacional]
   
   static fetchMode = [detalle: 'eager']

   static mapping = {
		table 'solterapiaocupacional'	
		id column:'idnota'
		paciente column:'idpaciente'
		servicio column:'idservicio'
		usuario column:'idusuario'
		version false
   }
   
}
