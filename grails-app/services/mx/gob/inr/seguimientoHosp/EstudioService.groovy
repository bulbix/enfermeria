package mx.gob.inr.seguimientoHosp

import mx.gob.inr.seguimientoHosp.agendas.*;
import mx.gob.inr.utils.Paciente

class EstudioService {

    
	def consultarTipoAgendas(Date fechaCita, Paciente paciente){		
		
		def result = []
		
		def importeTotal = 0.0
		
		def agendasList = ['Resonancia Magnetica':AgendaResonanciaMagnetica, 
			'Rayos X':AgendaRayosX, 
			'Tomografia':AgendaTomografia,
			'UltraSonido':AgendaUltraSonido,
			'Medicina Nuclear':AgendaMedicinaNuclear]
		
		
		def query = {			
			eq("fechacita",fechaCita)
			eq("paciente", paciente)
			ne("estatus","CANCELADA")			
		}		
		
		agendasList.each{ descripcion, agendaClass ->			
			
			List<Agenda> agendas = agendaClass.createCriteria().list(query) as List<Agenda>
			
			def importe = agendas?.sum{ a -> a.costo}?:0.0
			
			importeTotal += importe?:0.0
									
			result << [descripcion:descripcion, agendas: agendas, importe:importe]			
		}	
		
		[tiposAgenda:result, importeTotal:importeTotal]
		
	}
		
	
}
