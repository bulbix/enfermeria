package mx.gob.inr.utils

class Servicio {
	
	String descripcion

    static constraints = {
    }
	
	static mapping = {
		table 'servicio'
		version false
		id column:'idservicio'
		descripcion column:'descservicio'
	}
	
	String toString(){
		return  descripcion
	}
}
