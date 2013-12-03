package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.utils.Cie09;
import mx.gob.inr.utils.Paciente;
import mx.gob.inr.utils.OperacionQuirurgica;

class CirugiaService {

    def consultarNotasOperatoria(SeguimientoHosp seguimientoHosp) {
		
		
		def notas = Notaquirposopera.createCriteria().list{
			eq("fechaelaboracion",seguimientoHosp.fechaElaboracion)	
			eq("paciente",seguimientoHosp.paciente)
		}.each{ nota->	
		
			nota.notaDetalle = Operanotaquirposopera.findAllWhere(nota:nota)
			
			nota.notaDetalle.each{ operacion ->				
				operacion.costo = SeguimientoHospCirugia.findWhere(nota:nota,
					seguimientoHosp:seguimientoHosp,diagnostico:operacion.diagnostico,
					tipoOperacion:operacion.tipoOperacion)?.costo?:0.0		
			}
		}
		
		def importeTotal = importeTotalCirugia(seguimientoHosp.id)
		
				
		[notas:notas, importeTotal:importeTotal]
    }
	
	
	def guardarCostoCirugia(Long idSeguimiento, Long idNota, Long idDiagnostico, Float costo){
		
		def seguimientoHosp = SeguimientoHosp.read(idSeguimiento)
		def nota = Notaquirposopera.read(idNota)
		def diagnostico = Cie09.read(idDiagnostico)
		
		def seguimientoHospCirugia = SeguimientoHospCirugia.findWhere(seguimientoHosp:seguimientoHosp,
		nota:nota,diagnostico:diagnostico,tipoOperacion:OperacionQuirurgica.PRACTICADA)
		
		
		if(seguimientoHospCirugia){
			seguimientoHospCirugia.costo = costo
		}
		else{
			seguimientoHospCirugia =  new SeguimientoHospCirugia(
				seguimientoHosp:seguimientoHosp,
				nota:nota,
				diagnostico:diagnostico,
				tipoOperacion:OperacionQuirurgica.PRACTICADA,
				costo:costo
			)
			
		}
		
		seguimientoHospCirugia.save([validate:false])
				
	}
	
	def importeTotalCirugia(Long idSeguimiento){
		
		def seguimientosHospCirugia = SeguimientoHospCirugia.createCriteria().list{
			eq("seguimientoHosp.id", idSeguimiento)
		}
		
		def importeTotal = seguimientosHospCirugia?.sum{ a -> a.costo}?:0.0
		
		importeTotal
		
	}
	
}
