package mx.gob.inr.utils

class Cama {

	Short numerocama
	Area area
	
    static constraints = {
    }
	
	
	static mapping = {
		table 'cama'
		version false
		id column:'idcama'
		area column:'idarea'		
		
	}
	
	String toString(){
		return  ""
	}
}
