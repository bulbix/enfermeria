package mx.gob.inr.utils

class AdmisionHospitalaria {

	Paciente paciente
	Servicio servicio
	Area area
	Date fechaIngreso
	Date fechaEgreso
	Cama cama
	String estadoadmision
	Diagnostico diagnosticoIngreso
	
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
		cama column:'idcama'
		diagnosticoIngreso column:'iddiagingreso'
		
	}
	
	String toString(){
		return  ""
	}
	
	static transients = ['diasHosp']
	
	Integer getDiasHosp(){
		
		def duration = 0
		
		use(groovy.time.TimeCategory) {
			duration =(new Date()) - fechaIngreso
		
			if(fechaEgreso)
				duration = fechaEgreso - fechaIngreso
				
			duration  = duration.days
		}
		
		
		return duration
		
	}
	
}
