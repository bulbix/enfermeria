package mx.gob.inr.seguimientoHosp.agendas

class AgendaTomografia extends Agenda {

	static mapping = {
		table 'agendatomografia'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'
		estudio column:'estudio'
		version false
	}
    
}
