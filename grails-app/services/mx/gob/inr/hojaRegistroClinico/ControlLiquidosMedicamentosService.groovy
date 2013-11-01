package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.utils.Liquido
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.catalogos.*
import mx.gob.inr.seguridad.*
import grails.converters.*

class ControlLiquidosMedicamentosService {	
	
	def guardarLiquido(params,Usuario usuario, Short rubro){
		
		
		if(rubro in [R_MEDICAMENTOS,R_ESCALAGLASGOW_OTROS]){
			params.horafin = params.horainicio
		}
		
		for(hora in (params.horainicio as int)..(params.horafin as int)){
			
			def ingreso = new RegistroIngresoEgreso()
			
			ingreso.properties = params
			ingreso.hora = hora
			ingreso.usuario = usuario
			ingreso.hoja = HojaRegistroEnfermeria.get(params.idHoja)
			ingreso.rubro = CatRubroNotaEnfermeria.get(rubro)
			ingreso.save([validate:false])			
		}
	}	
	

	def guardarIngreso(params,Usuario usuario){		
		guardarLiquido(params,usuario,R_INGRESOS)
	}	
	
	def guardarEgreso(params,Usuario usuario){
		guardarLiquido(params,usuario,R_EGRESOS)
	}
	
	def guardarMedicamento(params,Usuario usuario){
		guardarLiquido(params,usuario,R_MEDICAMENTOS)
	}
	
	def guardarEscalaOtro(params,Usuario usuario){
		guardarLiquido(params,usuario,R_ESCALAGLASGOW_OTROS)
	}
	
	
	def guardarFaltante(params,Usuario usuario){
		
		def procedimientos = [P_FALTANTE_PASAR_MATUTINO,P_FALTANTE_PASAR_VESPERTINO,P_FALTANTE_PASAR_NOCTURNO];
		
		def fxp = JSON.parse(params.fxp)
		
		procedimientos.eachWithIndex { procedimiento, index->
			def ingreso = new RegistroIngresoEgreso()
			ingreso.descripcion = params.descripcion
			
			ingreso.totalingresar = fxp[index]
			ingreso.hora = null
			ingreso.usuario = usuario
			ingreso.hoja = HojaRegistroEnfermeria.get(params.idHoja)
			ingreso.procedimiento = CatProcedimientoNotaEnfermeria.get(procedimiento)
			ingreso.save([validate:false])
		}
		
		
		
	}
	
	def consultarIngresos(Long idHoja){
		
		def result = []
		
		def registros = RegistroIngresoEgreso.createCriteria().list(){
			projections{
				distinct("descripcion")
			}
			
			eq("hoja.id",idHoja)
			eq("rubro.id",R_INGRESOS as long)
		}.each{descripcion->
		
			def ingreso = new Liquido(descripcion:descripcion)
		
			def faltantes = [fxpM:P_FALTANTE_PASAR_MATUTINO,
				fxpV:P_FALTANTE_PASAR_VESPERTINO,fxpN:P_FALTANTE_PASAR_NOCTURNO]
			
			faltantes.each {propiedad,procedimiento->
				ingreso."$propiedad" = RegistroIngresoEgreso.createCriteria().get{
					projections{
						property("totalingresar")
					}
					eq("hoja.id",idHoja)
					eq("procedimiento.id",procedimiento as long)
					eq("descripcion",descripcion)
					maxResults(1)
				}?:"0"
				
			}
		
			result << ingreso
		
		}
		
		def ingresosDefault = [new Liquido(descripcion:'Medicamento Oral'),new Liquido(descripcion:'Via Oral')]
		
		ingresosDefault.each{ingreso->
			if(!result.contains(ingreso))
				result << ingreso
		}
		
		result
	}
	
	def consultarMedicamentos(Long idHoja){
		
		def registros = RegistroIngresoEgreso.createCriteria().list(){
			projections{
				distinct("descripcion")
			}
			
			eq("hoja.id",idHoja)
			eq("rubro.id",R_MEDICAMENTOS as long)
		}
		
		if(!registros)
			registros << ""
		
		registros
		
	}
	
	def consultarEscalaOtros(Long idHoja){
		
		def registros = RegistroIngresoEgreso.createCriteria().list(){
			projections{
				distinct("descripcion")
			}
			
			eq("hoja.id",idHoja)
			eq("rubro.id",R_ESCALAGLASGOW_OTROS as long)
		}
		
		def escalaOtrosDefault =[] 
		escalaOtrosDefault << "Respuesta Motora" << "Respuesta Ocular" << "Respuesta Verbal" 
		escalaOtrosDefault << "Posicion en cama" << "Perimetros" << "Glucosa Capilar"
		
		escalaOtrosDefault.each{escalaOtro->
			if(!registros.contains(escalaOtro))
				registros << escalaOtro
		}
		
		
		registros
		
	}
	
	
	def consultarDetalleLiquidoHtml(Long idHoja, String descripcion,Integer numeroRenglon, Usuario usuario,Short rubro,
		 Integer idProcedimiento = null){
		
		def html = """
				
			<input type="button" value="Eliminar todos los registros" onclick="borrarAllDetalleIngreso(${numeroRenglon})"/>
				
				<div style="height:300px;overflow:auto;">
				<table id="tablaIngreso${descripcion}">
					<thead>
					<tr>						
						<th>
							Hora
						</th>
						<th>
							Registro
						</th>
						<th>
							Usuario
						</th>
						<th>
							Eliminar
						</th>
					</tr>
					</thead>
					<tbody>
			"""
	
		def registros = RegistroIngresoEgreso.createCriteria().list {
						
				eq("hoja.id",idHoja)
				eq("rubro.id",rubro as long)
				
				if(idProcedimiento){				
					eq("procedimiento.id",idProcedimiento as long)
				}
				else{
					eq("descripcion",descripcion)
				}
		}.each{registro->
		
			html += """
					<tr id="rowIngreso${registro.id}">				
						<td>${registro.hora}</td>
						<td>${registro.totalingresar}</td>
						<td>${registro.usuario}</td>
						<td><input type="button" value="Eliminar" onclick="borrarDetalleLiquido(${registro.id})"/></td>
					</tr>
				"""
		}
		
		html += "</tbody></table></div>"
		
		html
		
	}
	
	List<RegistroIngresoEgreso> consultarDetalleLiquido(Long idHoja, String descripcion,Short rubro){
	
		def registros = RegistroIngresoEgreso.createCriteria().list {
						
				eq("hoja.id",idHoja)
				eq("rubro.id",rubro as long)
				eq("descripcion",descripcion)
		}
		
		registros
		
	}
	
	List<RegistroIngresoEgreso> consultarDetalleIngreso (Long idHoja, String descripcion){
		consultarDetalleLiquido(idHoja, descripcion,R_INGRESOS)
	}
	
	List<RegistroIngresoEgreso> consultarDetalleMedicamento (Long idHoja, String descripcion){
		consultarDetalleLiquido(idHoja, descripcion,R_MEDICAMENTOS)
	}
	
	List<RegistroIngresoEgreso> consultarDetalleEscalaOtro (Long idHoja, String descripcion){
		consultarDetalleLiquido(idHoja, descripcion,R_ESCALAGLASGOW_OTROS)
	}
	
	
	List<RegistroIngresoEgreso> consultarDetalleEgreso (Long idHoja, String descripcion){
		consultarDetalleLiquido(idHoja, descripcion,R_EGRESOS)
	}
	
		
	def consultarDetalleIngresoHtml(Long idHoja, String descripcion,Integer numeroRenglon, Usuario usuario){		
		consultarDetalleLiquidoHtml(idHoja, descripcion, numeroRenglon, usuario, R_INGRESOS)
	}
	
	def consultarDetalleEgreso(Long idHoja, String descripcion,Integer numeroRenglon, Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, numeroRenglon, usuario, R_EGRESOS)
	}
	
	def consultarDetalleMedicamento(Long idHoja, String descripcion,Integer numeroRenglon, Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, numeroRenglon, usuario, R_MEDICAMENTOS)
	}
	
	def consultarDetalleEscalaOtro(Long idHoja, String descripcion,Integer numeroRenglon, Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, numeroRenglon, usuario, R_ESCALAGLASGOW_OTROS)
	}
	
	def borrarDetalleLiquido(Long idRegistro){
		RegistroIngresoEgreso.get(idRegistro).delete()
	}
	
	def borrarAllDetalleIngreso(Long idHoja, String descripcion){
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",R_INGRESOS as long)
			eq("descripcion",descripcion)
		}*.delete()
		
	}
	
	def listarLiquidos(String term, Short rubro){
		
		def aprox = "%" + term + "%"	
		
		
		def criteria = RegistroIngresoEgreso.createCriteria();
		
		def lista = criteria.list(){
			ilike("descripcion", aprox)
			
			projections {
				distinct ("descripcion")
			}
			
			eq("rubro.id", rubro as long)
			
			order("descripcion")
			maxResults(10)
		}
		
		def results = lista?.collect {			
			[id: it, value: it, label: it]
		}
		
		return results
	}
	
	def listarIngresos(String term){
		listarLiquidos(term, R_INGRESOS)
	}
	
	def listarMedicamentos(String term){
		listarLiquidos(term, R_MEDICAMENTOS)
	}
	
	def listarEscalaOtros(String term){
		listarLiquidos(term, R_ESCALAGLASGOW_OTROS)
	}
}
