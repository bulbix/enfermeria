package mx.gob.inr.seguimientoHosp

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import mx.gob.inr.utils.*
import mx.gob.inr.reportes.Util;
import mx.gob.inr.seguridad.*
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ENFERMERIA'])
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
		
		def resultTipoAgendas = estudioService.consultarTipoAgendas(seguimientoHosp.fechaElaboracion,
			 seguimientoHosp.paciente)
			
		def resultNotasOperatoria = cirugiaService.consultarNotasOperatoria(seguimientoHosp)
		
		def resultTipoAgendasTerapia = terapiaService.consultarAgendasTerapia(seguimientoHosp.fechaElaboracion,
			 seguimientoHosp.paciente)
		
		
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
	
	
	/*****
	 * Genera el reporte concentrado o detallado del seguimiento Hospitalario
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @param paciente
	 * @param fileSalida
	 * @return
	 */
	private def generarReporte(Date fechaInicio,Date fechaFin,Paciente paciente, Usuario usuario, String tipoReporte, 
		Double importeGlobal){
		HashMap<String, Object> parametros = new HashMap<String, Object>();		
		parametros.put('SUBREPORT_DIR',"${servletContext.getRealPath('/reports')}/")
		parametros.put('URL_RESOURCES_PATH',"${servletContext.getRealPath('/images')}/")
		parametros.locale =  new Locale("es","MX");
		
		parametros.tipoReporte = tipoReporte
		parametros.fechaInicio = fechaInicio
		parametros.fechaFin = fechaFin
		parametros.idPaciente = paciente?.id
		parametros.ID_USUARIO = usuario?.id	
		parametros.FIRMA_DIGITALIZADA = new ByteArrayInputStream(FirmaDigital.findWhere(id:usuario.id).datos);
		
		parametros.importeGlobal = importeGlobal
		
		def reportDef = new JasperReportDef(name:"seguimientoHosp/reporteSeguimientoHosp.jasper",
			fileFormat:JasperExportFormat.PDF_FORMAT,parameters:parametros)
		
		def bytes = jasperService.generateReport(reportDef).toByteArray()
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		def FileNameReport = String.format("%s(%s).pdf",tipoReporte, paciente?.nombreCompleto.replace(' ',''))
				
		Util.mostrarReporte(response,bis,'application/pdf',FileNameReport)
		
	}
	
	def reporteDiario(Long id) {				
		
		def seguimientoHosp = SeguimientoHosp.read(id)		
		
		def importeGlobal = seguimientoHospService.importeGlobal(seguimientoHosp)
				
		generarReporte(seguimientoHosp?.fechaElaboracion, seguimientoHosp?.fechaElaboracion, 
			seguimientoHosp?.paciente, seguimientoHosp?.usuario, "ReporteDiario", importeGlobal)				
	}
	
	def reporteEstancia(Long id){	
		
		def seguimientoHosp = SeguimientoHosp.read(id)		
		def fechaMinima = seguimientoHospService.fechaMinimaEstancia(seguimientoHosp.paciente.id)
		
		def importeGlobal = 0.0
		
		for(fecha in (fechaMinima..seguimientoHosp?.fechaElaboracion)){
			def seguimientoVar = SeguimientoHosp.findWhere(paciente:seguimientoHosp?.paciente, fechaElaboracion:fecha)
			if(seguimientoVar){
				importeGlobal += seguimientoHospService.importeGlobal(seguimientoVar)
			}
		}
				
		generarReporte(fechaMinima, seguimientoHosp?.fechaElaboracion, 
			seguimientoHosp?.paciente, seguimientoHosp?.usuario, "ReporteEstancia", importeGlobal)		
		
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
