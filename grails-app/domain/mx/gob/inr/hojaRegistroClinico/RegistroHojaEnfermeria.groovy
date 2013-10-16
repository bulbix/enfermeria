package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.seguridad.Usuario

class RegistroHojaEnfermeria {
	
	Integer horaregistrodiagva
	String registrodiagvalora
	HojaRegistroEnfermeria hoja
	CatProcedimientoNotaEnfermeria procedimiento
	Usuario usuario
	String otro
	
    static constraints = {
    }
	
	static mapping = {
		table 'registroshojaenfermeria'
		version false
		id column:'idregistro'
		id generator:'sequence' ,params:[sequence:'sq_registros_enfermeria']
		hoja column:'idhojaregistroenfe'
		usuario column:'idusuario'
		procedimiento column:'idprocedimiento'
		
	}
	
	String toString(){
		return  String.format("hora(%s)--valor(%s)",horaregistrodiagva ,procedimiento.descripcion)
	}
}
