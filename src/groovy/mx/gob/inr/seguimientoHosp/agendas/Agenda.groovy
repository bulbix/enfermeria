package mx.gob.inr.seguimientoHosp.agendas

import mx.gob.inr.seguimientoHosp.ReferenciaBancaria
import mx.gob.inr.seguimientoHosp.agendas.CatEstudiosLabora;
import mx.gob.inr.utils.Paciente

abstract class Agenda {
	
	Paciente paciente
	Integer folio
	Integer concepto
	ReferenciaBancaria referenciaBancaria	
	CatEstudiosLabora estudio	
	String estatus
	Integer recibo
	
	Date fechacita
	Date horacita
	
	Float getCosto(){
		
		def detalle = referenciaBancaria?.referenciaDetalle?.find{d->d.estudio == estudio}
		
		return detalle?.costo?:0
		
	}
	
}
