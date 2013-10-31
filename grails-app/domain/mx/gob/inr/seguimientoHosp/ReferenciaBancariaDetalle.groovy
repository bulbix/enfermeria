package mx.gob.inr.seguimientoHosp

class ReferenciaBancariaDetalle {

	ReferenciaBancaria referenciaBancaria
	Integer clavecobro
	Integer idconceptocobro
	Float costo
	Integer cantidadpagos  
	
	
	 static mapping = {
		table 'referenciabancariadetalle'
		version false
		
		id column:'idreferenciadetalle'
		referenciaBancaria column:'idreferencia'		
	}
}
