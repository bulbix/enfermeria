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
		cache usage: 'read-only'
	}

	String toString(){
		return  descripcion
	}
}
