package mx.gob.inr.utils

import java.util.Date;

class NotaHospIngresoHosp {

	Paciente paciente
	
    Float peso
	Float talla
	
	
    static constraints = {
    }
	
	static mapping = {
		id column:'idnota'
		table 'notahospingresohosp'
		paciente column:'idpaciente'
		version false
	}
	
	String toString(){
		return  ""
	}
}
