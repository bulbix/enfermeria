package mx.gob.inr.utils

class Religion {
	
	String descripcion
	
	static constraints = {
	}

	static mapping = {
		table 'religion'
		version false
		id column:'idreligion'
		descripcion column:'descreligion'
	}

	String toString(){
		return  descripcion
	}
}
