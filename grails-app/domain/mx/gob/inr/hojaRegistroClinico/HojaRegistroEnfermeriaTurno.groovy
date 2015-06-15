package mx.gob.inr.hojaRegistroClinico

import java.util.Date;

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.Turno

import org.codehaus.groovy.grails.plugins.orm.auditable.*

class HojaRegistroEnfermeriaTurno {
	
	transient springSecurityService
	
	static auditable = [ignore:['firmada','fechaCaptura','firmaJefe','firmaSupervisor','firmaTraslado1','firmaTraslado2','firmaTraslado3']]
		
	
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
	
	
	String ipUsuario
	String ipTraslado1
	String ipTraslado2
	String ipTraslado3
	String ipJefe
	String ipSupervisor
	Date fechaUsuario = new Date()
	Date fechaTraslado1
	Date fechaTraslado2
	Date fechaTraslado3
	Date fechaJefe
	Date fechaSupervisor
	
	

    static constraints = {
		jefe(nullable:true)
		supervisor(nullable:true)
		traslado1(nullable:true)
		traslado2(nullable:true)
		traslado3(nullable:true)
		
		ipUsuario(nullable:true)
		ipTraslado1(nullable:true)
		ipTraslado2(nullable:true)
		ipTraslado3(nullable:true)
		ipJefe(nullable:true)
		ipSupervisor(nullable:true)
		fechaTraslado1(nullable:true)
		fechaTraslado2(nullable:true)
		fechaTraslado3(nullable:true)
		fechaJefe(nullable:true)
		fechaSupervisor(nullable:true)
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
	
}
