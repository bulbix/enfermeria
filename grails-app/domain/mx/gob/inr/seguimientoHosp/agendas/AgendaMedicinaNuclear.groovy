package mx.gob.inr.seguimientoHosp.agendas

class AgendaMedicinaNuclear extends Agenda {
		
	static mapping = {
		table 'agendamedicinanuclear'
		id column:'idcita'
		paciente column:'idpaciente'
		estudio column:'estudio'
		version false
	}
	
}
