package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.seguimientoHosp.terapias.*
import mx.gob.inr.utils.Paciente;

class TerapiaService {
	
	def consultarAgendasTerapia(Date fechaCita, Paciente paciente){
		
		def result = []
		
		def importeTotal = 0.0
		
		def agendasList = ['fisica','ocupacional']	
		
		agendasList.each{ descripcion ->
			
			def query = {
				eq("fechacita",fechaCita)
				eq("paciente", paciente)
				ne("estatus","CANCELADA")
				eq("tipoterapia", descripcion)
			}
			
			def agendas = AgendaTerapia.createCriteria().list(query)
			
			def importe = agendas?.sum{ a -> a.costo}?:0.0
			
			importeTotal += importe?:0.0
									
			result << [descripcion:descripcion, agendas: agendas, importe:importe]
		}
		
		[tiposAgenda:result, importeTotal:importeTotal]
		
	}
   
}
