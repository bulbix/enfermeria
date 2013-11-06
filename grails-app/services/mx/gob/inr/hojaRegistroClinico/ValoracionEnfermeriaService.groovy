package mx.gob.inr.hojaRegistroClinico

import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.catalogos.*
import mx.gob.inr.seguridad.*
import mx.gob.inr.utils.UtilService;

class ValoracionEnfermeriaService {
	
	UtilService utilService

    List<RegistroHojaEnfermeria> consultarRequisitos(Long idHoja){
		
		List<RegistroHojaEnfermeria> requisitos = [
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DESARROLLO)),
			new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_DESVIACION_SALUD))]
		
		requisitos = requisitos.collect{ requisito->
			
			def registro = RegistroHojaEnfermeria.createCriteria().get{
				eq("hoja.id", idHoja)
				eq("procedimiento.id",requisito.procedimiento.id)
				maxResults(1)
			}
			
			if(registro){
				requisito = registro
			}
		}
		
		requisitos
	}
	
}
