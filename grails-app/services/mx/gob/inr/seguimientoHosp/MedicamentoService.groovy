package mx.gob.inr.seguimientoHosp

import java.util.Date;

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

    
}
