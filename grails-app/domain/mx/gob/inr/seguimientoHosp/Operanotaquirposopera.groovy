package mx.gob.inr.seguimientoHosp

import mx.gob.inr.utils.Cie09
import mx.gob.inr.utils.OperacionQuirurgica;
import mx.gob.inr.utils.TipoDiagnostico

class Operanotaquirposopera implements Serializable {
	
	Notaquirposopera nota
	Cie09 diagnostico
	OperacionQuirurgica tipoOperacion
	TipoDiagnostico tipoDiagnostico	
	
	Float costo
	
	static transients = ['costo']

     static mapping = {
		id composite: ['nota','diagnostico','tipoOperacion']
		table 'operanotaquirposopera'				
		nota column:'idnota'
		diagnostico column:'iddiagnostico'
		tipoOperacion column:'tipodiag'
		tipoDiagnostico column:'principal'
		version false
	}
}
