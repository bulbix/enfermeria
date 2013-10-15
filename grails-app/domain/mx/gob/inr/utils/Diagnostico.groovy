package mx.gob.inr.utils

class Diagnostico {

   String descripcion

    static constraints = {
    }
	
	static mapping = {
		table 'diagnostico'
		version false
		id column:'iddiagnostico'
		descripcion column:'descdiagnostico'
	}
	
	String toString(){
		return  descripcion
	}
}
