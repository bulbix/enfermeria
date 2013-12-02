package mx.gob.inr.seguimientoHosp.terapias

import java.util.Date;

import mx.gob.inr.seguimientoHosp.ReferenciaBancaria;
import mx.gob.inr.seguimientoHosp.agendas.CatEstudiosLabora;
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
		
	/*Float getCosto(){
		def detalle = referenciaBancaria?.referenciaDetalle?.find{d->d.estudio == estudio}		
		return detalle?.costo?:0						
	}*/	
	
	static mapping = {
		table 'agendaterapia'
		id column:'idcita'
		paciente column:'idpaciente'
		referenciaBancaria column:'referencia'		
		version false
	}
	    
}
