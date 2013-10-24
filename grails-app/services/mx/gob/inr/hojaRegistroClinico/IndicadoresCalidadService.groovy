package mx.gob.inr.hojaRegistroClinico

import java.util.List;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*

class IndicadoresCalidadService {

	List<RegistroIngresoEgreso> consultarDetallePrevensionCaidas(Long idHoja, String descripcion,Short rubro){
		
			def registros = RegistroIngresoEgreso.createCriteria().list {
							
					eq("hoja.id",idHoja)
					eq("rubro.id",R_PREVENSION_CAIDAS as long)
					eq("descripcion",descripcion)
			}
			
			registros
			
	}
	
	
	
	
	
}
