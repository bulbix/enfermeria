package mx.gob.inr.utils

class AdmisionHospitalaria {

	Paciente paciente
	Servicio servicio
	Area area
	Date fechaIngreso
	Date fechaEgreso
	
    static constraints = {
    }
	
	static mapping = {
		table 'admisionhospitalaria'		
		version false
		id column:'idadmision'
		paciente column:'idpaciente'
		servicio column:'idservicio'
		area column:'idarea'
		fechaIngreso column:'fechaingreso'
		fechaEgreso column:'fechaegreso'
		
	}
	
	String toString(){
		return  ""
	}
}
