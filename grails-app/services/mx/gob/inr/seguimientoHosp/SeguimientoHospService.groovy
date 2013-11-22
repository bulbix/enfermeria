package mx.gob.inr.seguimientoHosp

import java.util.Date;
import mx.gob.inr.reportes.Util

class SeguimientoHospService {
	
	def consultarSeguimiento(Long idSeguimiento){

		def seguimientoHosp = SeguimientoHosp.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idSeguimiento)
			maxResults(1)
		}
		
		return seguimientoHosp

	}

    def consultarSeguimientos(Long idPaciente){
		
		def html = """		
			
			<div style="height:500px;overflow:auto;" class="wrapper" >
			<table id="tablaSeguimientos">
			<thead>			
					<tr>						
						<th>
							Fecha Elaboracion
						</th>						
						<th>
							Cargar
						</th>						
					</tr>			
			</thead><tbody>

		"""
		
		SeguimientoHosp.createCriteria().list{
			eq("paciente.id",idPaciente)
			order("fechaElaboracion","desc")
		}.each{ seguimiento->			
		
			html += """
				<tr>				
					<td>${seguimiento.fechaElaboracion.format('dd/MM/yyyy')}</td>
					<td><input type="button" value="ACEPTAR" onclick="consultarSeguimiento(${seguimiento.id},'${seguimiento.fechaElaboracion.format('dd/MM/yyyy')}')"/></td>					
				</tr>
			"""
		}
		
		html += "</tbody></table></div>"
		
		html
		
	}
	
	boolean seguimientoSoloLectura(Date fechaElaboracion){
		
		boolean soloLectura = true
		
		Date fechaActual = Util.getFechaDate(Util.getFechaActual("yyyy-MM-dd"));
		
		
		if(fechaElaboracion.compareTo(fechaActual) == 0){
			soloLectura = false
		}
		
		
		soloLectura
		
	}
}
