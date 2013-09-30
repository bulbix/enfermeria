package mx.gob.inr.utils

class Paciente {
	
	String nombre
	String paterno
	String materno
	String numeroregistro
	String idtipopaciente

    static constraints = {
    }
	
	static mapping = {
		id column:'idpaciente'
		version false
	}
	
	String toString(){
		return  String.format("(%s) %s %s %s",numeroregistro[0..12] ,paterno, materno, nombre)
	}
}
