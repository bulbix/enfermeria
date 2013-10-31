package mx.gob.inr.seguimientoHosp

import mx.gob.inr.utils.Paciente

class ReferenciaBancaria {

	Paciente paciente
	Integer idnivelsocioeconomico
	Float importe
	String referencia
	
	
	
    static mapping = {
		table 'referenciabancaria'
		version false
		
		id column:'idreferencia'
		paciente column:'idpaciente'			
				
	}
}
