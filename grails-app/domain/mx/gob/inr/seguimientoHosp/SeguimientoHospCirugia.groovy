package mx.gob.inr.seguimientoHosp

import mx.gob.inr.utils.Cie09;
import mx.gob.inr.utils.OperacionQuirurgica;
import mx.gob.inr.utils.TipoDiagnostico;

class SeguimientoHospCirugia {

   SeguimientoHosp seguimientoHosp   
   Notaquirposopera nota   
   Cie09 diagnostico   
   OperacionQuirurgica tipoOperacion   
   Float costo
	
    static mapping = {
		table 'seguimiento_hosp_cirugia'
		version false		
		id generator:'identity'
		seguimientoHosp column:'idseguimiento'
		nota column:'idnota'
		diagnostico column:'iddiagnostico'		
		tipoOperacion column:'tipodiag'				
	}
}
