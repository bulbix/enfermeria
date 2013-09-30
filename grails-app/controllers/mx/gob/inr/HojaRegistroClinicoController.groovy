package mx.gob.inr

import org.grails.plugins.wsclient.service.WebService

class HojaRegistroClinicoController {
	
	WebService webService

    def index() {
		
		def wsdlUrl = "http://localhost:8090/almacenWeb/services/autoComplete?wsdl"
		def proxy = webService.getClient(wsdlUrl)

		String result = proxy.listarPaciente("GONZALEZ")
		render result
		
	}
	
	
	def create(){
		
	}
	
	
	
}
