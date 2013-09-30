package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.seguridad.Usuario

class HojaRegistroEnfermeriaTurno {
	
	enum Turno {MATUTINO(1), VESPERTINO(2), NOCTURNO(3)}
	
	HojaRegistroEnfermeria hoja
	Usuario usuario 
	Turno turno
	Boolean firmada
	Date fechaCaptura
	Boolean firmaJefe
	Boolean firmaSupervisor
	Usuario jefe
	Usuario supervisor
	Boolean firmaTraslado1
	Boolean firmaTraslado2
	Boolean firmaTraslado3
	Usuario traslado1
	Usuario traslado2
	Usuario traslado3
	

    static constraints = {
    }
	
	static mapping = {
		table 'hojaregistroenfermeriaturno'
		version false
		id column:'consecutivo'
		id generator:'identity'
		hoja column:'idhoja'
		usuario column:'idusuario'
		turno column:'turno'
		fechaCaptura column:'fechacaptura'
		firmaJefe column:'firmajefe'
		firmaTraslado1 column:'firmatraslado1'
		firmaTraslado2 column:'firmatraslado2'
		firmaTraslado3 column:'firmatraslado3'		
		
	}
}
