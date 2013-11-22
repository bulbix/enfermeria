package mx.gob.inr.seguimientoHosp

import java.util.Date;

class SeguimientoHospMedicamento {

   SeguimientoHosp seguimientoHosp
   ArticuloFarmacia articulo
   Integer cantidad
   Float precioUnitario
	
    static mapping = {
		table 'seguimiento_hosp_medicamento'
		version false		
		id generator:'identity'
		seguimientoHosp column:'idseguimiento'				
		articulo column:'cve_art'
						
	 }
}
