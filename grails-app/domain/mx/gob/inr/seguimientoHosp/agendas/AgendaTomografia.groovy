package mx.gob.inr.seguimientoHosp.agendas

class AgendaTomografia extends Agenda {
		
	static mapping = {
		table 'agendatomografia'
		id column:'idcita'
		paciente column:'idpaciente'
		estudio column:'estudio'
		version false
	}
}
