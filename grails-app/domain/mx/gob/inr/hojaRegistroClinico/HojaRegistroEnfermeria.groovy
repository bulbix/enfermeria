package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.utils.AdmisionHospitalaria
import mx.gob.inr.utils.Paciente

class HojaRegistroEnfermeria {
	
	AdmisionHospitalaria admision
	Paciente paciente
	Date fechaElaboracion
	Float peso
	Float talla
	String alergias
	String comorbilidad
	String otros
	
	
    static constraints = {
    }
	
	
	static mapping = {
		table 'hojaregistroenfermeria'
		version false
		id column:'idhojaregistroenfe'
		id generator:'sequence' ,params:[sequence:'sq_hoja_enfermeria']
		admision column:'idadmision'
		paciente column:'idpaciente'
		fechaElaboracion column:'fechaelaboracion'
		
	}
}
