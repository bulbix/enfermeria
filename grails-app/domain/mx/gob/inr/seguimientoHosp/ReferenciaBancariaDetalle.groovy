package mx.gob.inr.seguimientoHosp

import mx.gob.inr.seguimientoHosp.agendas.CatEstudiosLabora;

class ReferenciaBancariaDetalle {

	ReferenciaBancaria referenciaBancaria
	Integer clavecobro
	Integer idconceptocobro
	Float costo
	Integer cantidadpagos  
	CatEstudiosLabora estudio
	
	 static mapping = {
		table 'referenciabancariadetalle'
		id column:'idreferenciadetalle'
		referenciaBancaria column:'idreferencia'
		estudio column:'idestudio'
		version false
	}
}
