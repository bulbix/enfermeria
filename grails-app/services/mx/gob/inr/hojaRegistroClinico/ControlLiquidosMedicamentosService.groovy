package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.utils.Ingreso
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import mx.gob.inr.catalogos.*
import mx.gob.inr.seguridad.*
import grails.converters.*

class ControlLiquidosMedicamentosService {

	def guardarIngreso(params,Integer idUsuario){
		
		for(hora in (params.horainicio as int)..(params.horafin as int)){
			
			def ingreso = new RegistroIngresoEgreso()
			
			ingreso.properties = params
			ingreso.hora = hora
			ingreso.usuario = Usuario.get(idUsuario)
			ingreso.hoja = HojaRegistroEnfermeria.get(params.idHoja)
			ingreso.rubro = CatRubroNotaEnfermeria.get(R_INGRESOS)
			ingreso.save([validate:false])
			
		}
	}
	
	def guardarFaltante(params,Integer idUsuario){
		
		def procedimientos = [P_FALTANTE_PASAR_MATUTINO,P_FALTANTE_PASAR_VESPERTINO,P_FALTANTE_PASAR_NOCTURNO];
		
		def fxp = JSON.parse(params.fxp)
		
		procedimientos.eachWithIndex { procedimiento, index->
			def ingreso = new RegistroIngresoEgreso()
			ingreso.descripcion = params.descripcion
			
			ingreso.totalingresar = fxp[index]
			ingreso.hora = null
			ingreso.usuario = Usuario.get(idUsuario)
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
		
			def ingreso = new Ingreso(descripcion:descripcion)
		
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
		
		result << new Ingreso(descripcion:'Medicamento Oral') << new Ingreso(descripcion:'Via Oral')
		
		if(!result){
			result << new Ingreso()
		}
		
		result
	}
		
	def consultarDetalleIngreso(Long idHoja, String descripcion,Integer numeroRenglon, Integer idUsuario){
		
		//def html = new StringBuffer()
		
		def html = """
				
			<input type="button" value="Eliminar todos los registros" onclick="borrarAllDetalleIngreso(${numeroRenglon})"/>
				
				<table id="tablaIngreso${descripcion}">
					<thead>
					<tr>						
						<th>
							Hora
						</th>
						<th>
							Cantidad
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
				eq("rubro.id",R_INGRESOS as long)
				eq("descripcion",descripcion)
		}.each{registro->
		
			html += """
				<tr id="rowIngreso${registro.id}">				
					<td>${registro.hora}<td>
					<td>${registro.totalingresar}</td>
					<td>${registro.usuario}</td>
					<td><input type="button" value="Eliminar" onclick="borrarDetalleIngreso(${registro.id})"/></td>
				</tr>
			"""
		}
		
		html += "</tbody></table>"
		
		html
		
	}
	
	def borrarDetalleIngreso(Long idRegistro){
		RegistroIngresoEgreso.get(idRegistro).delete()
	}
	
	def borrarAllDetalleIngreso(Long idHoja, String descripcion){
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",R_INGRESOS as long)
			eq("descripcion",descripcion)
		}*.delete()
		
	}
	
	
}
