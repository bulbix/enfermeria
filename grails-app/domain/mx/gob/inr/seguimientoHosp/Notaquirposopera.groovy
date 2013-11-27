package mx.gob.inr.seguimientoHosp

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.OperacionQuirurgica;
import mx.gob.inr.utils.Paciente
import mx.gob.inr.utils.Servicio

class Notaquirposopera {
	
	Paciente paciente
	Servicio servicio
	Date fechaelaboracion
	Usuario usuario
	
	
	static hasMany = [notaDetalle:Operanotaquirposopera]	
	
	Set<Operanotaquirposopera> getOperacionesPracticadas(){		
		notaDetalle.findAll { d -> d.tipoOperacion == OperacionQuirurgica.PRACTICADA}		
	}

     static mapping = {
		table 'notaquirposopera'	
		id column:'idnota'
		paciente column:'idpaciente'
		servicio column:'idservicio'
		usuario column:'idusuario'
		version false
	}
}
