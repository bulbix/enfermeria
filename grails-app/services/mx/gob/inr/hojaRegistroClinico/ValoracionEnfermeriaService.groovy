package mx.gob.inr.hojaRegistroClinico

import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.catalogos.*
import mx.gob.inr.seguridad.*
import mx.gob.inr.utils.UtilService;

class ValoracionEnfermeriaService {
	
	UtilService utilService

    List<RegistroHojaEnfermeria> consultarRequisitos(Long idHoja){
		def requisitos = RegistroHojaEnfermeria.createCriteria().list{
			procedimiento{
				eq("padre.id",R_REQUISITOS as long)
				order("id")
			}
			
			eq("hoja.id", idHoja)
			
		}
		
		//Los centinelas
		if(!requisitos){
			requisitos << new RegistroHojaEnfermeria() << new RegistroHojaEnfermeria();
		}
		
		requisitos
	}
	
	
	def guardarValoracionEnfermeria(Long idHoja,Integer idUsuario, List requisitos){		
		
		def idRequisitos = [P_REQUESITO_DESARROLLO, P_REQUESITO_DESVIACION]
		
		idRequisitos.eachWithIndex { idRequisito, index ->
			utilService.
			guardarRegistroEnfermeria(null,idHoja,idRequisito,idUsuario,requisitos[index],true)
		}		
	}
	
}
