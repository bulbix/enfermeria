package mx.gob.inr.seguimientoHosp.agendas

import mx.gob.inr.seguimientoHosp.Agenda;

class AgendaResonanciaMagnetica extends Agenda {   
	
	static mapping = {
		table 'agendaresonanciamagnetica'		
		id column:'idcita'
		paciente column:'idpaciente'
		estudio column:'estudio'
		version false
	}
	
	
}
