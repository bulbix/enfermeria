package mx.gob.inr.seguimientoHosp.agendas

class AgendaResonanciaMagnetica extends Agenda {

	static mapping = {
		table 'agendaresonanciamagnetica'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'
		estudio column:'estudio'
		version false
	}
}
