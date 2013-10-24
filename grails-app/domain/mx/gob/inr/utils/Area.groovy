package mx.gob.inr.utils

class Area {

	String descripcion
	String clavearea
	
    static constraints = {
		
	}	
	
	static mapping = {
		table 'area'
		version false
		id column:'idarea'
		descripcion column:"descarea"
		cache true
	}
	
	String toString(){
		return  descripcion
	}
}
