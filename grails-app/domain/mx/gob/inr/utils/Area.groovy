package mx.gob.inr.utils

class Area {

	String descripcion
	
    static constraints = {
		
	}	
	
	static mapping = {
		table 'area'
		version false
		id column:'idarea'
		descripcion column:"descarea"		
	}
	
	String toString(){
		return  descripcion
	}
}
