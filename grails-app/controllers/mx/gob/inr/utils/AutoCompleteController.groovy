package mx.gob.inr.utils

import grails.converters.JSON

class AutoCompleteController {

	UtilService utilService	
	
    def consultarServicios() {
		
		long idArea = params.long('idArea')		
		def servicios = utilService.consultarServicios(idArea)
		render servicios as JSON		
	}
	
	
	def consultarPacientes(){
		String term = params.term
		long idArea = params.long('idArea')
		long idServicio = params.long('idServicio')
		boolean historico = params.boolean('historico')
		
		def pacientes = utilService.consultarPacientes(term,idArea,idServicio,historico)
		render pacientes as JSON
	}
	
	def consultarDatosPaciente(){
		Long idPaciente = params.long('idPaciente')
		def admision  = utilService.consultarDatosPaciente(idPaciente)
		
		def result = [edad:admision.paciente.fechanacimiento.age + " anos",sexo:admision.paciente.sexo,
		religion:admision.paciente.datosPaciente.toArray()[0].religion.descripcion,
		cama:admision.cama.numerocama,diasHosp:admision.diasHosp,servicio:admision.servicio.descripcion,
		diagnostico:admision.diagnosticoIngreso.descripcion]
		
		render result as JSON
		
	}
}
