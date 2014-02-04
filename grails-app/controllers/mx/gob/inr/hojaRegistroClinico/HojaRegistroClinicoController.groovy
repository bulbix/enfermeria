package mx.gob.inr.hojaRegistroClinico

import javax.servlet.ServletOutputStream
import javax.servlet.http.Cookie
import mx.gob.inr.catalogos.*;
import mx.gob.inr.reportes.ReporteRegistrosClinicos;
import mx.gob.inr.reportes.Util;
import mx.gob.inr.utils.*;
import grails.converters.JSON



import static mx.gob.inr.utils.ConstantesHojaEnfermeria.*
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ENFERMERIA'])
class HojaRegistroClinicoController {	
	
	def reporteHojaFacadeService
	def hojaRegistroClinicoService
	def utilService
	def springSecurityService
	def jefeSupervisorService
	
	
	/***
	 * Pantalla Principal de la hoja
	 * @return
	 */
	def index(){		
		def hojaInstance = new HojaRegistroEnfermeria()
		hojaInstance.rubrosValoracion = utilService.consultarCatalogoRubro(S_VALORACION)
		hojaInstance.rubrosDiagnostico = utilService.consultarCatalogoRubro(S_DIAGNOSTICOS_INTERVENCIONES)
		hojaInstance.rubrosIndicador = utilService.consultarCatalogoRubro(S_INDICADORES_CALIDAD,true)
		def pisos = utilService.consultarPisos()		
		def usuarioActual = springSecurityService.currentUser
		
		hojaInstance.fechaElaboracion = hojaRegistroClinicoService.asignarFechaElaboracion()
				
		[hojaInstance:hojaInstance,pisos:pisos,usuarioActual:usuarioActual]
	}
		
	def consultarHoja(){
		
		def idHoja=params.long('idHoja')
		def turnoActual=params.turnoActual
		def mensaje = params.mensaje
		def nuevaHoja = params.boolean('nuevaHoja')
		def pantallaJefeSupervisor = params.boolean('pantallaJefeSupervisor')
		
		
		def hojaInstance = hojaRegistroClinicoService.consultarHoja(idHoja,turnoActual)			
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser		
		def soloLectura = hojaRegistroClinicoService.hojaSoloLectura(hojaInstance?.fechaElaboracion)
		
		def jefeSupervisor = false
		
		
		if(nuevaHoja){//Cargamos los historicos
			def hojaHistorica = hojaRegistroClinicoService.cargarHojaHistorica(hojaInstance.paciente.id,hojaInstance?.fechaElaboracion)
			
			if(hojaHistorica){
				
				hojaInstance.dietas = hojaHistorica.dietas
				
				hojaInstance.dietas.each { dieta->
					dieta.discard()
					dieta.id=null
					dieta.hoja = hojaInstance
				}*.save([validate:false])
				
				hojaInstance.requisitos = hojaHistorica.requisitos
				
				hojaInstance.requisitos.each { requisito->
					requisito.discard()
					requisito.id=null
					requisito.hoja = hojaInstance
				}*.save([validate:false])
			}
			
			def religion = new RegistroHojaEnfermeria(procedimiento:CatProcedimientoNotaEnfermeria.get(P_RELIGION),
				hoja:hojaInstance,usuario:usuarioActual,otro:hojaInstance.paciente.datosPaciente?.toArray()[0]?.religion?.descripcion)
			religion.save([validate:false])
		}
		
		if(!hojaRegistroClinicoService.duenoTurno(idHoja, turnoActual,springSecurityService.currentUser)){
			soloLectura = true
		}

		//Si tiene perfil de jefe o supervisor la hoja se abrira para modificacion cualquier fecha y viene de la pantalla de jefe supervisor
		if(pantallaJefeSupervisor && utilService.isJefeSupervisor(usuarioActual)){
			soloLectura = false
			jefeSupervisor = true
		}		
		
				
		def model = [hojaInstance:hojaInstance,pisos:pisos,mensaje:mensaje,
		usuarioActual:usuarioActual,soloLectura:soloLectura, jefeSupervisor:jefeSupervisor]
				
		render(view:'index', model:model);		
	}
	
	///######## INICIA SUBVISTAS#################
	def alergiasComorbilidad(){		
	}
	
	def signosVitales(){
	}
	
	def controlLiquidosMedicamentos(){
	}
	
	def valoracionEnfermeria(){
	}
	
	def diagnosticosIntervenciones(){		
	}
	
	def indicadoresCalidad(){		
	}	
	///########FIN SUBVISTAS#################
	
	def guardarHojaTurno(){
		
		def jsonHoja = JSON.parse(params.dataHoja)		
		def fechaElaboracion = new Date().parse("dd/MM/yyyy",jsonHoja.fechaElaboracion)
		
		def result = hojaRegistroClinicoService.existeHoja(jsonHoja.idPaciente as long, fechaElaboracion)
		
		if(result.existe){//Guarda o actualiza la hoja e inserta turno
			jsonHoja.idHoja = result.idHoja
			def hoja = hojaRegistroClinicoService.guardarHojaTurno(jsonHoja,springSecurityService.currentUser)
			render(contentType: 'text/json') {['status':'existeHoja']}			
		}
		else{
			
			def idHoja = 0 //Hoja nueva
			
			if(jsonHoja.idHoja)
				idHoja = jsonHoja.idHoja as long
			
			def htmlTabla = hojaRegistroClinicoService.mostrarFirma(idHoja)
			render(contentType: 'text/json') {['html': htmlTabla,'status':'firmarHoja']}
		}
		
	}
	
	def consultarHojas(){		
		def htmlTabla = hojaRegistroClinicoService.consultarHojas(params.long('idPaciente'))		
		render(contentType: 'text/json') {['html': htmlTabla]}		
	}
	
	
	def mostrarFirma(){		
		def turnoAsociar = params.turnoAsociar?:'MATUTINO'
		def tieneUsuario = false;
		
		def fechaElaboracion
		
		if(params.fechaElaboracion){
			fechaElaboracion = new Date().parse("dd/MM/yyyy",params.fechaElaboracion)
		}
		
		if(params.tieneUsuario){
			tieneUsuario = params.boolean('tieneUsuario')
		}
		
		def tipoUsuario = params.tipoUsuario
		
		if(tipoUsuario.startsWith("Traslado") || ['Jefe','Supervisor'].contains(tipoUsuario) ){			
			def htmlTabla = hojaRegistroClinicoService.mostrarFirma(params.long('idHoja'), tieneUsuario,tipoUsuario)
			render(contentType: 'text/json') {['html': htmlTabla,'status':'firmarHoja','soloLectura':false]}
		}		
		
		def soloLectura = hojaRegistroClinicoService.hojaSoloLectura(fechaElaboracion)
		
		if(hojaRegistroClinicoService.existeTurno(params.long('idHoja'), turnoAsociar)){
			if(!hojaRegistroClinicoService.duenoTurno(params.long('idHoja'), turnoAsociar,
				springSecurityService.currentUser)){
				soloLectura = true
			}			
			render(contentType: 'text/json') {['status': 'cargarHoja','soloLectura':soloLectura]}
		}
		else{			
			def htmlTabla = hojaRegistroClinicoService.mostrarFirma(params.long('idHoja'), tieneUsuario,tipoUsuario)
			render(contentType: 'text/json') {['html': htmlTabla,'status':'firmarHoja',
				'soloLectura':soloLectura]}			
		}
					
	}
	
	def firmarHoja(){
		def password = params.passwordFirma
		Long idHoja = params.long('idHoja')
		def turnoAsociar = params.turnoAsociar
		def jsonHoja = JSON.parse(params.dataHoja)
		def idUsuarioFirma = params.int('idUsuarioFirma')
		def tipoUsuarioFirma = params.tipoUsuarioFirma
		
		if(!idHoja){//Firma jefe supervisor traslado
			if(jsonHoja.idHoja)//NUeva hoja
				idHoja = jsonHoja.idHoja as long
				
			turnoAsociar = jsonHoja.turno
		}		
		
		def result =	hojaRegistroClinicoService.
		firmarHoja(idHoja,turnoAsociar, springSecurityService.currentUser, password,jsonHoja,
		idUsuarioFirma,tipoUsuarioFirma)
		
		render(contentType: 'text/json') {
			['firmado':result.firmado,'idHoja':result.idHoja,'nuevaHoja':result.nuevaHoja]}
	}	
	
	def reporteHoja(){
		
		Cookie reportCookie = new Cookie("fileDownloadToken", params.download_token_value_id);
		response.addCookie(reportCookie)
		
		def reporte = new ReporteRegistrosClinicos(reporteHojaFacadeService)
		def hojaInstance = hojaRegistroClinicoService.consultarHoja(params.long('idHoja'))		
				
		if(hojaInstance){
			
			hojaInstance.imageDir = "${servletContext.getRealPath('/images')}/"
			
			byte[] bytes = reporte.generarReporte(hojaInstance)
			def datos =  new ByteArrayInputStream(bytes)		
			String cadenaRandom = Util.getCadenaAlfanumAleatoria(8);				
			def FileNameReport = String.format("HOJAENF(%s)(%s).pdf",
				hojaInstance.paciente.nombreCompleto.replace(' ',''),				
				hojaInstance.fechaElaboracion.format('ddMMyyyy'))		 
			Util.mostrarReporte(response,datos,'application/pdf',FileNameReport)
		}
		else{
			redirect(action:'index')
		}
	}
	
	@Secured(['ROLE_ENFERMERIA_JEFE_SUPERVISOR'])
	def jefeSupervisor(){
				
		def result = []
		
		def pisos = utilService.consultarPisos()
		pisos.each{ piso->			
			piso.pacientes= jefeSupervisorService.consultarPacientes(piso.id)
		}
			
		[pisos:pisos]
	}
	
	
	
	def misHojas(Long idUsuario){		
		def htmlTabla = hojaRegistroClinicoService.misHojas(params.long('idUsuario'), params.turno)
		render(contentType: 'text/json') {['html': htmlTabla]}		
	}	
		
}
