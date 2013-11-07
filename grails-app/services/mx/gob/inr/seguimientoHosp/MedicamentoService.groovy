package mx.gob.inr.seguimientoHosp

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
		def articulo = ArticuloFarmacia.get(id)
		articulo.desArticulo = articulo.desArticulo.trim()
		return articulo
	}

    
}
