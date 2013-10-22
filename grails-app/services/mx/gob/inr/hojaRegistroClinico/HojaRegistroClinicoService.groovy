package mx.gob.inr.hojaRegistroClinico

import mx.gob.inr.catalogos.CatProcedimientoNotaEnfermeria;
import mx.gob.inr.catalogos.CatRubroNotaEnfermeria;
import mx.gob.inr.utils.*;
import mx.gob.inr.seguridad.*;
import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import grails.converters.*


class HojaRegistroClinicoService {

	static transactional = true

	/***
	 * 
	 * @param params
	 * @return la hoja
	 */
	def guardarHojaTurno(params, Integer idUsuario){

		def hoja = new HojaRegistroEnfermeria()

		if(params.idHoja)
			hoja = HojaRegistroEnfermeria.get(params.idHoja)

		hoja.properties = params
		hoja.has = params.has == 'on'
		hoja.dm = params.dm == 'on'
		hoja.nef = params.nef == 'on'
		hoja.ic = params.ic == 'on'
		hoja.ir = params.ir == 'on'

		hoja.asignarComorbilidad()

		hoja.admision = AdmisionHospitalaria.get(params.idAdmision)
		hoja.paciente  = Paciente.get(params.idPaciente)		
		
		def hojaTurno = new HojaRegistroEnfermeriaTurno(
			hoja:hoja,
			usuario:Usuario.get(idUsuario),
			turno:Turno."${params.turno}"			
		)	
		
		
		hoja.save([validate:false])
		hojaTurno.save([validate:false])
		
		hoja

	}

	/***
	 * Consulta de manera completa hoja de enfermeria
	 * @param idHoja
	 * @return hoja
	 */
	def consultarHoja(Long idHoja){

		def hoja = HojaRegistroEnfermeria.createCriteria().get{
			admision{
			}
			paciente{
			}
			eq("id",idHoja)
		}
		
		hoja.signosVitales = consultarSignosVitales(idHoja)
		hoja.escalaDolor = consultarEscalaDolor(idHoja)
		hoja.dietas = consultarDietas(idHoja)
		hoja.requisitos = consultarRequisitos(idHoja)
		
		hoja.rubrosValoracion = consultarCatalogoRubro(S_VALORACION)
		hoja.rubrosDiagnostico = consultarCatalogoRubro(S_DIAGNOSTICOS_INTERVENCIONES)
		
		hoja.ingresos = consultarIngresos(idHoja)

		return hoja
	}
	
	def consultarSignosVitales(Long idHoja){
		
		def result = []
		
		def procedimientosSignos = ['temperatura':P_TEMEPRATURA,
			'cardiaca':P_FRECUENCIA_CARDIACA,
			'diastolica':P_TENSION_ARTERIAL_DIASTOLICA,
			'sistolica':P_TENSION_ARTERIAL_SISTOLICA,
			'respiracion':P_FRECUENCIA_RESPIRATORIA,
			'gabinete':P_LABORATORIO_GABINETE]
		
		procedimientosSignos.each{ propiedad, procedimientoSigno ->
			
			def registros = RegistroHojaEnfermeria.createCriteria().list(){
				eq("hoja.id",idHoja)
				eq("procedimiento.id",procedimientoSigno as long)
			}.each{
			
				def signoVital = result.find { s -> s.hora == it.horaregistrodiagva }
				
				if(signoVital){
					signoVital."${propiedad}" = it
				}
				else{
					signoVital = new SignoVital()
					signoVital.hora = it.horaregistrodiagva
					signoVital."${propiedad}" = it
					result << signoVital
				}
			}			
		}	
		
		//un valor centinela para agregar mas registros
		if(!result){
			result << new SignoVital(hora:1)
		}
		
		Collections.sort(result)
		
		result
		
	}
	
	def guardarSignosVitales(Long idHoja, Integer idUsuario, List horas,List temperaturaList, List cardiacaList, 
		List sistolicaList, List diastolicaList, List respiracionList, List gabineteList){
		
		horas.eachWithIndex { hora, index ->
			guardarRegistroEnfermeriaConValor(hora as int,
			idHoja,P_TEMEPRATURA,idUsuario,temperaturaList[index])
			
			guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_FRECUENCIA_CARDIACA,idUsuario,cardiacaList[index])
			
			guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_TENSION_ARTERIAL_SISTOLICA,idUsuario,sistolicaList[index])
			
			guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_TENSION_ARTERIAL_DIASTOLICA,idUsuario,diastolicaList[index])
			
			guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_FRECUENCIA_RESPIRATORIA,idUsuario,respiracionList[index])
			
			guardarRegistroEnfermeriaConValor(hora as int,
				idHoja,P_LABORATORIO_GABINETE,idUsuario,gabineteList[index])			
		}	
		
	}
		
	def consultarEscalaDolor(Long idHoja){
		def registros = RegistroHojaEnfermeria.createCriteria().list(){
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_ESCALA_DOLOR as long)
			}
		}
		
		registros
		
	}
	
	def guardarEscalaDolor(String dolor, Long idHoja, Integer hora, Integer idUsuario){
		
		def procedimiento = CatProcedimientoNotaEnfermeria.createCriteria().get{
			eq("padre.id",R_ESCALA_DOLOR as long)
			eq("descripcion",dolor)
		}
		
		guardarRegistroEnfermeriaSinValor(hora ,idHoja,procedimiento.id,idUsuario)
		
	}
		
	/***
	 * El rodenamiento es basico para un correcto despliegue
	 * @param idHoja
	 * @return
	 */
	def consultarDietas(Long idHoja){
		
		def registros = RegistroHojaEnfermeria.createCriteria().list(){
			eq("hoja.id",idHoja)
			procedimiento{
				eq("padre.id",R_DIETA as long)
				order("id")
			}
			
			
		}
		
		registros
		
	}
		
	def guardarDietas(Long idHoja, Integer idUsuario, List dietas, List horaDietas){
		
		def procedimientos = [P_DIETA_DIETA,P_DIETA_MATUTINO,P_DIETA_VESPERTINO,P_DIETA_NOCTURNO];
				
		procedimientos.eachWithIndex { procedimiento, index->							
			guardarRegistroEnfermeria(horaDietas[index] as int,idHoja,procedimiento,idUsuario, dietas[index],true)
		}
		
	}	
	
	/****
	 * Guardar registro con valor 
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @param valor
	 * @return
	 */
	def guardarRegistroEnfermeriaConValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario,String valor,Boolean modificarHora = false, String propiedadValor = "otro"){
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("procedimiento.id",idProcedimiento as long)
			
			if(!modificarHora){
				eq("horaregistrodiagva",hora)
			}
			
			eq("hoja.id",idHoja)
		}
		
		if(registro){
			
			if(valor){
				
				if(valor=="00000"){//Una condicion para eliminar registros sin ningun check					
					registro.delete()
					return
				}
								
				registro."$propiedadValor" = valor
				
				if(modificarHora){
					registro.horaregistrodiagva = hora
				}
				
				registro.save([validate:false])
			}
			else{
				registro.delete()
			}			
		}
		else{
			if(valor){
				registro = new RegistroHojaEnfermeria()				
				registro.horaregistrodiagva = hora
				registro.hoja = HojaRegistroEnfermeria.get(idHoja)
				registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
				registro.usuario =Usuario.get(idUsuario)
				registro."$propiedadValor" = valor
				registro.save([validate:false])
			}
		}
			
	}
	
	/***
	 * Guarda registros con valor o sin valor y no borra
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @param valor
	 * @return
	 */
	def guardarRegistroEnfermeria(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario,String valor,Boolean modificarHora = false){
		
		def registro = RegistroHojaEnfermeria.createCriteria().get{
			eq("procedimiento.id",idProcedimiento as long)
			
			if(!modificarHora){
				eq("horaregistrodiagva",hora)
			}
			
			eq("hoja.id",idHoja)
		}
		
		if(registro){
			registro.otro = valor
				
			if(modificarHora){
				registro.horaregistrodiagva = hora
			}
				
			registro.save([validate:false])
		}
		else{
			registro = new RegistroHojaEnfermeria()
			registro.horaregistrodiagva = hora
			registro.hoja = HojaRegistroEnfermeria.get(idHoja)
			registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
			registro.usuario =Usuario.get(idUsuario)
			registro.otro = valor
			registro.save([validate:false])
		}
		
	}
	
	/***
	 * Guardar registros sin valor
	 * @param hora
	 * @param idHoja
	 * @param idProcedimiento
	 * @param idUsuario
	 * @return
	 */
	def guardarRegistroEnfermeriaSinValor(Integer hora, Long idHoja,Long idProcedimiento,Integer idUsuario){
				
		def registro = new RegistroHojaEnfermeria()
		registro.horaregistrodiagva = hora
		registro.hoja = HojaRegistroEnfermeria.get(idHoja)
		registro.procedimiento = CatProcedimientoNotaEnfermeria.get(idProcedimiento)
		registro.usuario =Usuario.get(idUsuario)
		registro.otro = ""
		registro.save([validate:false])
	}
	
	def consultarCatalogoRubro(Long idSegmento){
		def rubros = CatRubroNotaEnfermeria.createCriteria().list{
			eq("padre.id",idSegmento)
			order("descripcion")
		}
		
		rubros
	}
	
	def guardarCheckTabla(Long idHoja,Long idProcedimiento,Integer idUsuario,Turno turno, Boolean valor){
		
		def result = RegistroHojaEnfermeria.createCriteria().get{
			
			projections{
				property("registrodiagvalora")
			}
			
			eq("procedimiento.id",idProcedimiento as long)			
			eq("hoja.id",idHoja)
		}
		
		if(!result)
			result = new StringBuffer("00000")
		else 
			result = new StringBuffer(result)
			
			 
		result.setCharAt(turno.id-1, valor==true?'1' as char:'0' as char)
		
		guardarRegistroEnfermeriaConValor(null,idHoja,idProcedimiento,idUsuario,result.toString(),true,"registrodiagvalora")
	}
	
	def consultarCheckTabla(Long idHoja, long idProcedimiento){
		
				
		def result = RegistroHojaEnfermeria.createCriteria().get{
			
			projections{
				property("registrodiagvalora")
			}
			
			eq("hoja.id", idHoja)
			eq("procedimiento.id", idProcedimiento )
		}
		
		
		if(!result)
			result ="00000"
		
		result	
		
	}	
	
	def consultarRequisitos(Long idHoja){
		def requisitos = RegistroHojaEnfermeria.createCriteria().list{
			procedimiento{
				eq("padre.id",R_REQUISITOS as long)
				order("id")
			}
			
			eq("hoja.id", idHoja)
			
		}
		
		requisitos
	}
	
	def guardarIngreso(params,Integer idUsuario){
		
		//def ingreso = RegistroIngresoEgreso.get(params.id)
		
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
