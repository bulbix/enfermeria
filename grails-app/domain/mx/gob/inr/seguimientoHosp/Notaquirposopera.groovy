package mx.gob.inr.seguimientoHosp

import mx.gob.inr.utils.Paciente

class Notaquirposopera {
	
	Paciente paciente
	
	

     static mapping = {
		table 'notaquirposopera'
		version false
		
		id column:'idnota'
		paciente column:'idpaciente'		
	}
}
