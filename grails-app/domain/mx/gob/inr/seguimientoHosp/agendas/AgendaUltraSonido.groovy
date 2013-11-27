package mx.gob.inr.seguimientoHosp.agendas

class AgendaUltraSonido extends Agenda {

	static mapping = {
		table 'agendaultrasonido'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'
		estudio column:'estudio'
		version false
	}
   
}
