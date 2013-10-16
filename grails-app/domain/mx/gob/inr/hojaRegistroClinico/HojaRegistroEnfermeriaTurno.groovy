package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.Turno

class HojaRegistroEnfermeriaTurno {	
	
	HojaRegistroEnfermeria hoja
	Usuario usuario 
	Turno turno
	Boolean firmada = true
	Date fechaCaptura = new Date()
	Boolean firmaJefe = false
	Boolean firmaSupervisor=false
	Usuario jefe
	Usuario supervisor
	Boolean firmaTraslado1=false
	Boolean firmaTraslado2=false
	Boolean firmaTraslado3=false
	Usuario traslado1
	Usuario traslado2
	Usuario traslado3
	

    static constraints = {
    }
	
	static mapping = {
		table 'hojaregistroenfermeriaturno'
		version false		
		id generator:'identity', column:'consecutivo'
		hoja column:'idhoja'
		usuario column:'idusuario'		
		fechaCaptura column:'fechacaptura'
		firmaJefe column:'firmajefe'
		firmaSupervisor column:'firmasupervisor'
		jefe column:'idjefe'
		supervisor column:'idsupervisor'
		firmaTraslado1 column:'firmatraslado1'
		firmaTraslado2 column:'firmatraslado2'
		firmaTraslado3 column:'firmatraslado3'
		traslado1 column:'idtraslado1'
		traslado2 column:'idtraslado2'
		traslado3 column:'idtraslado3'		
		
	}
	
	def beforeInsert(){
		/*firmada =false
		fechaCaptura = new Date()
		firmaJefe =false
		firmaSupervisor=false		
		firmaTraslado1=false
		firmaTraslado2=false
		firmaTraslado3=false*/
		
	}
}
