package mx.gob.inr.seguimientoHosp.agendas

class AgendaMedicinaNuclear extends Agenda {

	static mapping = {
		table 'agendamedicinanuclear'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'
		estudio column:'estudio'
		version false
	}
    
}
