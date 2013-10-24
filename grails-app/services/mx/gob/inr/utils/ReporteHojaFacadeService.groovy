package mx.gob.inr.utils

import java.util.List;

import mx.gob.inr.hojaRegistroClinico.RegistroHojaEnfermeria;
import mx.gob.inr.hojaRegistroClinico.RegistroIngresoEgreso;
import mx.gob.inr.seguridad.FirmaDigital;
import mx.gob.inr.seguridad.Usuario

class ReporteHojaFacadeService {
	
	def signosVitalesService
	def controlLiquidosMedicamentosService
	def utilService
	def indicadoresCalidadService

    FirmaDigital consultarFirma(Usuario usuario) {		
		FirmaDigital.findWhere(id:usuario.id)
    }
	
	List<RegistroHojaEnfermeria> consultarEscalaDolor(Long idHoja){
		signosVitalesService.consultarEscalaDolor(idHoja)
	}	
	
	List<RegistroIngresoEgreso> consultarDetalleIngreso(Long idHoja,String descripcion){
		controlLiquidosMedicamentosService.consultarDetalleIngreso(idHoja, descripcion)
	}
	
	List<RegistroIngresoEgreso> consultarDetalleEgreso(Long idHoja,String descripcion){
		controlLiquidosMedicamentosService.consultarDetalleEgreso(idHoja, descripcion)
	}
	
	List<RegistroIngresoEgreso> consultarDetalleMedicamento(Long idHoja,String descripcion){
		controlLiquidosMedicamentosService.consultarDetalleMedicamento(idHoja, descripcion)
	}
	
	List<RegistroIngresoEgreso> consultarDetalleEscalaOtro(Long idHoja,String descripcion){
		controlLiquidosMedicamentosService.consultarDetalleEscalaOtro(idHoja, descripcion)
	}
	
	List<RegistroIngresoEgreso> consultarDetallePrevensionCaidas(Long idHoja,String descripcion){
		indicadoresCalidadService.consultarDetallePrevensionCaidas(idHoja, descripcion)
	}
	
	List<RegistroHojaEnfermeria> consultarCheckRubro(Long idHoja,Long idRubro){
		utilService.consultarCheckRubro(idHoja,idRubro)
	}
	
	List<RegistroIngresoEgreso> consultarIngresoRubro(Long idHoja,Long idRubro){
		utilService.consultarIngresoRubro(idHoja,idRubro)
	}
	
	
}
