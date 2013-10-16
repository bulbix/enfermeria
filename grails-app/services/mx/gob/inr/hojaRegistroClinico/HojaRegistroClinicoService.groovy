package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.utils.*;

class HojaRegistroClinicoService {
	
	static transactional = true
	
	/***
	 * 
	 * @param params
	 * @return la hoja
	 */
	def guardarAlergiasComorbilidad(params){
		
		def hoja = new HojaRegistroEnfermeria()
		
		if(params.idHoja)
			hoja = HojaRegistroEnfermeria.get(params.idHoja)
		
		hoja.properties = params
		hoja.has = params.has == 'on'
		hoja.dm = params.dm == 'on'
		hoja.nef = params.nef == 'on'
		hoja.ic = params.ic == 'on'
		hoja.ir = params.ir == 'on'
	
		hoja.asignarComorbilidad()
	
		
		hoja.admision = AdmisionHospitalaria.get(params.idAdmision)
		hoja.paciente  = Paciente.get(params.idPaciente)		
		hoja.save([validate:false])
		
		hoja
		
	}
	
	
	def consultar(Long idHoja){
		
		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
				
			}
			
			paciente{
				
			}
			
			eq("id",idHoja)
			
		}
		
		return hoja
		
		
	}
	
	

    
}
