package mx.gob.inr.seguimientoHosp.terapias

import java.util.Date;

import mx.gob.inr.seguimientoHosp.ReferenciaBancaria;
import mx.gob.inr.seguimientoHosp.agendas.CatEstudiosLabora;
import mx.gob.inr.utils.NotaMedica
import mx.gob.inr.utils.Paciente;

class AgendaTerapia {
	
	Paciente paciente
	Integer folio	
	ReferenciaBancaria referenciaBancaria	
	Date fechacita
	Date horacita
	Integer recibo
	String estatus
	Integer sesiones
	String tipoterapia
	Integer idsolicitud
	
	
	List getTerapias(){
		
		def result = null
		
		switch(tipoterapia){			
			case "fisica":
				result = ModalidadTerapiaFisica.findAllWhere(idnota: idsolicitud)			
				break	
			case "ocupacional":
				result = ActividadOcupacional.findAllWhere(idnota: idsolicitud)
				break
		}
		
		return result
		
	}
		
	Float getCosto(){
		def costo = referenciaBancaria?.referenciaDetalle?.toArray()[0]?.costo / sesiones		
		return costo?:0						
	}	
	
	static transients = ["costo","estudios"]
	
	static mapping = {
		table 'agendaterapia'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'		
		version false
	}
	    
}
