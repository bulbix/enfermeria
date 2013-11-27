package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.utils.Paciente;

class CirugiaService {

    def consultarNotasOperatoria(Date fechaElaboracion, Paciente paciente) {
		
		
		def notas = Notaquirposopera.createCriteria().list{
			eq("fechaelaboracion",fechaElaboracion)	
			eq("paciente",paciente)
		}.each{ nota->
			nota.notaDetalle = Operanotaquirposopera.findAllWhere(nota:nota)
		}
				
		notas

    }
}
