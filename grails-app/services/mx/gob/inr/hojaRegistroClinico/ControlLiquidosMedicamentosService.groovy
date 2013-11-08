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
		
		def horaInicio = params.horainicio as int
		def horaFin = params.horafin as int
		
		if(horaInicio > horaFin ){			
			for(hora in horaInicio..24){
				guardarRegistroIngresoEgreso(params,hora,usuario,rubro)
			}
			
			for(hora in 1..horaFin){
				guardarRegistroIngresoEgreso(params,hora,usuario,rubro)
			}
		}
		else{
			
			for(hora in horaInicio..horaFin){			
				guardarRegistroIngresoEgreso(params,hora,usuario,rubro)
			}
			
		}		
	}
	
	def guardarRegistroIngresoEgreso(params,Integer hora, Usuario usuario,Short rubro){
		
		def ingreso = new RegistroIngresoEgreso()
		ingreso.properties = params
		ingreso.hora = hora
		ingreso.usuario = usuario
		ingreso.hoja = HojaRegistroEnfermeria.get(params.idHoja)
		ingreso.rubro = CatRubroNotaEnfermeria.get(rubro)
		ingreso.save([validate:false])
		
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
		
		def ingresosDefault = [new Liquido(descripcion:'Medicamento Oral',etiqueta:true),
			new Liquido(descripcion:'Via Oral',etiqueta:true), new Liquido(soloLectura:false)]
		
		ingresosDefault.each{ingreso->
			if(!result.contains(ingreso)){
				result << ingreso
			}
			else{				
				result.find{ l -> l.descripcion == ingreso.descripcion }.etiqueta = true
			}
		}
		
		result
	}
	
	def consultarMedicamentos(Long idHoja){
		
		def result = []
		
		def registros = RegistroIngresoEgreso.createCriteria().list(){
			projections{
				distinct("descripcion")
			}
			
			eq("hoja.id",idHoja)
			eq("rubro.id",R_MEDICAMENTOS as long)
		}.each{descripcion->
		
			def medicamento = new Liquido(descripcion:descripcion)
			
			result << medicamento
		
		}
		
		if(!result)
			result << new Liquido(soloLectura:false)
		
		result
		
	}
	
	def consultarEscalaOtros(Long idHoja){
		
		def result = []
		
		def registros = RegistroIngresoEgreso.createCriteria().list(){
			projections{
				distinct("descripcion")
			}
			
			eq("hoja.id",idHoja)
			eq("rubro.id",R_ESCALAGLASGOW_OTROS as long)
		}.each{descripcion->
		
			def escalaOtro = new Liquido(descripcion:descripcion)
			
			result << escalaOtro
		
		}
		
		def escalaOtrosDefault =[] 
		escalaOtrosDefault << new Liquido(descripcion:"Respuesta Motora",etiqueta:true) << new Liquido(descripcion:"Respuesta Ocular",etiqueta:true)	<< 
		new Liquido(descripcion:"Respuesta Verbal",etiqueta:true) << new Liquido(descripcion:"Posicion en cama",etiqueta:true) <<
		new Liquido(descripcion:"Perimetros",etiqueta:true) << new Liquido(descripcion:"Glucosa Capilar",etiqueta:true) << 
		new Liquido(soloLectura:false)
		
		escalaOtrosDefault.each{escalaOtro->
			if(!result.contains(escalaOtro)){
				result << escalaOtro
			}
			else{
				result.find{ l -> l.descripcion == escalaOtro.descripcion }.etiqueta = true
			}
		}
		
		
		result
		
	}
	
	
	def consultarDetalleLiquidoHtml(Long idHoja, String descripcion,Usuario usuario,Short rubro,
		 Integer idProcedimiento = null){
		
		def html = """
				
			<input type="button" class="operacion" id="eliminarMisRegistros" value="Eliminar mis registros"/>
				
				<div style="height:300px;overflow:auto;">
				<table>
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
				
				order("hora","asc")
				
				
		}.each{registro->
		
			html += """
					<tr id="filaLiquido${registro.id}">				
						<td>${registro.hora}</td>
						<td>${registro.totalingresar}</td>
						<td>${registro.usuario}</td>
						<td>${registro.usuario == usuario?"<input type=\"button\" class=\"operacion\" value=\"Eliminar\" onclick=\"borrarDetalleLiquido(${registro.id})\"/>":''}</td>
						
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
	
		
	def consultarDetalleIngresoHtml(Long idHoja, String descripcion, Usuario usuario){		
		consultarDetalleLiquidoHtml(idHoja, descripcion, usuario, R_INGRESOS)
	}
	
	def consultarDetalleEgresoHtml(Long idHoja, String descripcion,Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, usuario, R_EGRESOS)
	}
	
	def consultarDetalleMedicamentoHtml(Long idHoja, String descripcion, Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, usuario, R_MEDICAMENTOS)
	}
	
	def consultarDetalleEscalaOtroHtml(Long idHoja, String descripcion, Usuario usuario){
		consultarDetalleLiquidoHtml(idHoja, descripcion, usuario, R_ESCALAGLASGOW_OTROS)
	}
	
	def borrarDetalleLiquido(Long idRegistro){
		RegistroIngresoEgreso.get(idRegistro).delete()
	}
	
	def borrarAllDetalleLiquido(Long idHoja, String descripcion, Usuario usuario, Short rubro, Long idProcedimiento=null){
		
		def registros = RegistroIngresoEgreso.createCriteria().list {
			eq("hoja.id",idHoja)
			eq("rubro.id",rubro as long)
			if(idProcedimiento){
				eq("procedimiento.id",idProcedimiento)
			}
			else{
				eq("descripcion",descripcion)
			}
			
			eq("usuario",usuario)
		}*.delete()
	}
	
	def borrarAllDetalleIngreso(Long idHoja, String descripcion, Usuario usuario){
		borrarAllDetalleLiquido(idHoja,descripcion,usuario,R_INGRESOS)
	}
	
	def borrarAllDetalleEgreso(Long idHoja, String descripcion, Usuario usuario){
		borrarAllDetalleLiquido(idHoja,descripcion,usuario,R_EGRESOS)
	}
	
	def borrarAllDetalleMedicamento(Long idHoja, String descripcion, Usuario usuario){
		borrarAllDetalleLiquido(idHoja,descripcion,usuario,R_MEDICAMENTOS)
	}
	
	def borrarAllDetalleEscalaOtro(Long idHoja, String descripcion, Usuario usuario){
		borrarAllDetalleLiquido(idHoja,descripcion,usuario,R_ESCALAGLASGOW_OTROS)
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
	
	
	def existeHoraLiquido(Long idHoja, String descripcion,Short rubro, Integer hora, Long idProcedimiento=null){
		
		def result = false
		
		def registro = RegistroIngresoEgreso.createCriteria().get{
			eq("hoja.id",idHoja)
			
			if(idProcedimiento){
				eq("procedimiento.id",idProcedimiento)
			}
			else{
				eq("descripcion",descripcion)
			}
			
			
			eq("rubro.id",rubro as long)
			eq("hora",hora)
			maxResults(1)			
		}
		
		if(registro){
			result = true
		}
		
		result
	}
	
	def existeHoraIngreso(Long idHoja, String descripcion, Integer hora){
		existeHoraLiquido(idHoja, descripcion, R_INGRESOS, hora)
	}
	
	def existeHoraEgreso(Long idHoja, String descripcion, Integer hora){
		existeHoraLiquido(idHoja, descripcion, R_EGRESOS, hora)
	}
	
	def existeHoraMedicamento(Long idHoja, String descripcion, Integer hora){
		existeHoraLiquido(idHoja, descripcion, R_MEDICAMENTOS, hora)
	}
	
	def existeHoraEscalaOtro(Long idHoja, String descripcion, Integer hora){
		existeHoraLiquido(idHoja, descripcion, R_ESCALAGLASGOW_OTROS, hora)
	}
	
	def cambiarLiquido(Long idHoja, String descripcionOld, String descripcionNew, Short rubro){
		
		def result = false
		
		def registros = RegistroIngresoEgreso.createCriteria().list{
			eq("hoja.id",idHoja)			
			eq("descripcion",descripcionOld)		
			eq("rubro.id",rubro as long)			
		}.each{ registro->
			registro.descripcion = descripcionNew
			registro.save([validate:false])
		}
		
		if(registros){
			result = true
		}
		
		result
	}
	
	
	def cambiarIngreso(Long idHoja, String descripcionOld, String descripcionNew){
		cambiarLiquido(idHoja, descripcionOld, descripcionNew, R_INGRESOS)
	}
	
	def cambiarMedicamento(Long idHoja, String descripcionOld, String descripcionNew){
		cambiarLiquido(idHoja, descripcionOld, descripcionNew, R_MEDICAMENTOS)
	}
	
	def cambiarEscalaOtro(Long idHoja, String descripcionOld, String descripcionNew){
		cambiarLiquido(idHoja, descripcionOld, descripcionNew, R_ESCALAGLASGOW_OTROS)
	}
	
}
