package mx.gob.inr.hojaRegistroClinico

class ValoracionEnfermeriaController {
	
	ValoracionEnfermeriaService valoracionEnfermeriaService

    def guardarValoracionEnfermeria(){		
		
		def requisitos = params.list('requisito')		
		valoracionEnfermeriaService.guardarValoracionEnfermeria(params.long('idHoja'), 6558, requisitos)
		
		render "Valoracion Enfermeria salvado correctamente"		
	}
}
