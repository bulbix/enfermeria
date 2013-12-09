package mx.gob.inr.seguimientoHosp

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils
import mx.gob.inr.reportes.Util;

class SeguimientoHospController {
	
	def utilService
	def springSecurityService
	def seguimientoHospService
	def estudioService
	def cirugiaService
	def terapiaService
	def jasperService

    def index() {		
		def seguimientoHosp = new SeguimientoHosp()
		
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser				
		[seguimientoHosp:seguimientoHosp,pisos:pisos,usuarioActual:usuarioActual,
		soloLectura:false, resultTipoAgendas:null, notaOperatoria:null]		
	}
	
	def consultarSeguimiento(){
		
		def idSeguimiento=params.long('idSeguimiento')	
		def mensaje = params.mensaje
		
		def seguimientoHosp = seguimientoHospService.consultarSeguimiento(idSeguimiento)
		
		def resultTipoAgendas = estudioService.
			consultarTipoAgendas(seguimientoHosp.fechaElaboracion, seguimientoHosp.paciente)
			
		def resultNotasOperatoria = cirugiaService.consultarNotasOperatoria(seguimientoHosp)
		
		def resultTipoAgendasTerapia = terapiaService.consultarAgendasTerapia(
			seguimientoHosp.fechaElaboracion, seguimientoHosp.paciente)
		
		
		def pisos = utilService.consultarPisos()
		def usuarioActual = springSecurityService.currentUser
		def soloLectura = seguimientoHospService.seguimientoSoloLectura(seguimientoHosp?.fechaElaboracion)
						
		def model = [seguimientoHosp:seguimientoHosp,pisos:pisos,mensaje:mensaje,
		usuarioActual:usuarioActual,soloLectura:soloLectura,
		resultTipoAgendas:resultTipoAgendas, resultNotasOperatoria:resultNotasOperatoria,
		resultTipoAgendasTerapia:resultTipoAgendasTerapia]
				
		render(view:'index', model:model);
	}
	
	def consultarSeguimientos(){
		def htmlTabla = seguimientoHospService.consultarSeguimientos(params.long('idPaciente'))
		render(contentType: 'text/json') {['html': htmlTabla]}
	}
	
	def existeFechaElaboracion(){
		def fechaElaboracion = new Date().parse("dd/MM/yyyy",params.fechaElaboracion)
		def result = seguimientoHospService.existeFechaElaboracion(params.long('idPaciente'),fechaElaboracion)
		render(contentType: 'text/json') {['result': result]}
	}
	
	private def parametrosReporte(){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put('SUBREPORT_DIR',"${servletContext.getRealPath('/reports')}/")
		parametros.put('URL_RESOURCES_PATH',"${servletContext.getRealPath('/images')}/")
		parametros.locale =  new Locale("es","MX");
		
		parametros	
	}
	
	def reporteDiario(Long id) {		
		
		def parametros = parametrosReporte()		
		
		def seguimientoHosp = SeguimientoHosp.read(id)
		
		parametros.fechaInicio = seguimientoHosp?.fechaElaboracion
		parametros.fechaFin = seguimientoHosp?.fechaElaboracion
		parametros.idPaciente = seguimientoHosp?.paciente?.id
		
		def reportDef = new JasperReportDef(name:'seguimientoHosp/reporteSeguimientoHosp.jasper',
			fileFormat:JasperExportFormat.PDF_FORMAT,parameters:parametros)
		
		def bytes = jasperService.generateReport(reportDef).toByteArray()
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);		
		Util.mostrarReporte(response,bis,'application/pdf',"ReporteDiario.pdf")				
	}
	
	def reporteEstancia(Long id){
		
		def parametros = parametrosReporte()
		
		def fechas = seguimientoHospService.fechasEstancia(id)
		
		parametros.fechaInicio = fechas?.fechaInicio
		parametros.fechaFin = fechas?.fechaFin
		parametros.idPaciente = id
		
		def reportDef = new JasperReportDef(name:'seguimientoHosp/reporteSeguimientoHosp.jasper',
			fileFormat:JasperExportFormat.PDF_FORMAT,parameters:parametros)
		
		def bytes = jasperService.generateReport(reportDef).toByteArray()
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Util.mostrarReporte(response,bis,'application/pdf',"ReporteEstancia.pdf")		
		
	}	
	
	def medicamento(){
	
	}

	def estudio(){
	
	}

	def cirugia(){
	
	}

	def terapia(){
	
	}
	
}
