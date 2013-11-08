package mx.gob.inr.utils

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

class AutoCompleteController {

	UtilService utilService
	def hojaRegistroClinicoService
	
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
		def pesoTalla = utilService.consultarPesoTalla(idPaciente)
		
		def result = [idAdmision:admision.id,edad:admision.paciente.fechanacimiento.age,sexo:admision.paciente.sexo,
		religion:admision.paciente.datosPaciente.toArray()[0].religion.descripcion,
		cama:admision.cama.numerocama,diasHosp:admision.diasHosp,servicio:admision.servicio.descripcion,
		diagnostico:admision.diagnosticoIngreso.descripcion,peso:pesoTalla[0],talla:pesoTalla[1],
		nombrePaciente:admision.paciente.nombreCompleto]
		
		def hojaHistorica = hojaRegistroClinicoService.cargarHojaHistorica(idPaciente)
		
		if(hojaHistorica){
			result['historico',true]
			result['hoja',hojaHistorica]
			result['has'] = hojaHistorica.has
			result['dm'] = hojaHistorica.dm
			result['nef'] = hojaHistorica.nef
			result['ic'] = hojaHistorica.ic
			result['ir'] = hojaHistorica.ir
		}
		else{
			result['historico',false]
		}
		
		def json = result as JSON 
			
		render json
		
	}
	
	
	def consultarEnfermeras(){
		String term = params.term
		
		def usuarios = utilService.consultarEnfermeras(term)
		render usuarios as JSON
	}
	
	
	
}
