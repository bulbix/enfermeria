package mx.gob.inr.seguimientoHosp

import java.util.Date;

import mx.gob.inr.seguridad.Usuario
import mx.gob.inr.utils.*;

class MedicamentoService {
	
	def listarArticulo(String term)	{
		def aprox = "%" + term + "%"
		
		def query = {
			ilike("desArticulo", aprox)
			maxResults(50)
			order("desArticulo","asc")
		}
		
		def alist = ArticuloFarmacia.createCriteria().list(query)
 
		def results = alist?.collect {
			def display = String.format("(%s) %s",it.id,it.desArticulo.trim())
			[id:it.id ,value:it.desArticulo.trim(),label:display]
		}
 
		return results
	}	
	
	def buscarArticulo(Long id){
		def articulo = ArticuloFarmacia.read(id)
		articulo?.precioCierre = importeUltimoCierre(articulo,"F")			
		articulo?.desArticulo = articulo?.desArticulo?.trim()
		return articulo
	}
	
	Double importeUltimoCierre(ArticuloFarmacia articulo, String almacen){
		
		def result = 0.0
				
		def criteria = CierreFarmacia.createCriteria()
		
		def fechaUltimoCierre = criteria.get {			
			projections {
				max("fechaCierre")
			}			
			eq("almacen", almacen)
		}
		
		def cierre = CierreFarmacia.findWhere(fechaCierre:fechaUltimoCierre, articulo:articulo)
		
		if(cierre)
			result = cierre.importe
		
		return result
	}

	SeguimientoHosp guardarSeguimientoHosp(jsonPadre, Usuario usuario){
		
		def seguimientoHosp = new SeguimientoHosp(
			admision:AdmisionHospitalaria.read(jsonPadre.idAdmision),
			paciente:Paciente.read(jsonPadre.idPaciente),
			usuario:usuario
		).save([validate:false])	
		
		return seguimientoHosp	
	}
	
	def guardarMedicamento(jsonDetalle, SeguimientoHosp seguimientoHosp){	
		
		def articulo  = buscarArticulo(jsonDetalle.cveArt as long)
		
		def seguimientoHospMedicamento = new SeguimientoHospMedicamento(
			seguimientoHosp:seguimientoHosp,
			articulo: articulo,
			cantidad: jsonDetalle.cantidad as int,
			precioUnitario: articulo.precioCierre
		).save([validate:false]) 		
	}
	
	def consultarDetalleMedicamento(params){	
		
		def sortIndex = params.sidx ?: 'id'
		def sortOrder  = params.sord ?: 'asc'
		def maxRows = Integer.valueOf(params.rows)
		def currentPage = Integer.valueOf(params.page) ?: 1
		def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
		def idSeguimiento  = params.long('idSeguimiento')	

		def detalleCount = SeguimientoHospMedicamento.createCriteria().list(){
			eq('seguimientoHosp.id',idSeguimiento)
		}
		
		def detalle = SeguimientoHospMedicamento.createCriteria()
			.list(max: maxRows, offset: rowOffset) {
			eq('seguimientoHosp.id',idSeguimiento)			
			order('id', 'asc')
		}
			
			
		def totalRows = detalleCount.size();
		def numberOfPages = Math.ceil(totalRows / maxRows)
	
		def importeTotal = 0
		
		def results = detalle?.collect {
			
			def importe = it.precioUnitario * it.cantidad
			importeTotal += importe
							
			[cell:[it.articulo.id,it.articulo.desArticulo?.trim(),
			it.articulo.unidad?.trim(),it.precioUnitario,it.cantidad, importe], id: it.id
			]
		}
	
		def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages, importeTotal: importeTotal]
		return jsonData
		
	}
    
	
	
}
