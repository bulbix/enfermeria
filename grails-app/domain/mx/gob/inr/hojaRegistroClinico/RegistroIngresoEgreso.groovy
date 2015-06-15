package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.seguridad.Usuario

class RegistroIngresoEgreso {
	
	HojaRegistroEnfermeria hoja
	Usuario usuario
	Integer hora
	String descripcion
	String volumen
	String totalingresar=""
	CatRubroNotaEnfermeria rubro
	CatProcedimientoNotaEnfermeria procedimiento
	
    static constraints = {
		hora(nullable:true)
		descripcion(nullable:true)
		volumen(nullable:true)
		totalingresar(nullable:true)
		rubro(nullable:true)
		procedimiento(nullable:true)
    }
	
	static mapping = {
		table 'registroingresoegresos'
		version false
		id column:'idingresosegresos'
		id generator:'sequence' ,params:[sequence:'sq_ingresosegresos_enfermeria']
		hoja column:'idhojaregistroenfe'
		usuario column:'idusuario'
		rubro column:'idrubro'
		procedimiento column:'idprocedimiento'
		procedimiento lazy: false
	}
}
