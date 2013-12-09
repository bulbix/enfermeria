package mx.gob.inr.seguimientoHosp

import static org.junit.Assert.*

import org.junit.*
import grails.converters.JSON

class SeguimientoHospTests {
	
	def seguimientoHospService
	def springSecurityService
	
	def shc = new SeguimientoHospController()

    @Before
    void setUp() {
        shc.seguimientoHospService = seguimientoHospService
    }

    @After
    void tearDown() {
        // Tear down logic here
    }
	
	@Test
	void "consultar Seguimientos"(){
		
		shc.params.idPaciente = 295751
		shc.consultarSeguimientos()
		
		def jsonResult = JSON.parse(shc.response.contentAsString)
		
		println jsonResult.html
		
		
	}
	
	@Test
	void "testear importe Global"(){		
		def seguimiento = SeguimientoHosp.read(82)	
		
		def importeGlobal = seguimientoHospService.importeGlobal(seguimiento)
		
		assertEquals(1764.39, importeGlobal,0.0 )		
		
	}
}
