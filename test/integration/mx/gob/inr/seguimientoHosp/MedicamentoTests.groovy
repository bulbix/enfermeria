package mx.gob.inr.seguimientoHosp

import static org.junit.Assert.*
import org.junit.*
import grails.converters.JSON
import mx.gob.inr.seguridad.*

class MedicamentoTests {
		
	def medicamentoService
	def springSecurityService
	
	def mc = new MedicamentoController()

    @Before
    void setUp() {
        mc.medicamentoService = medicamentoService
		//mc.springSecurityService = springSecurityService
    }

    @After
    void tearDown() {
        // Tear down logic here
    }
	
	def crearEscenario(){
		
			
		def jsonPadre = new groovy.json.JsonBuilder()
		jsonPadre(
			idAdmision: 178472,
			idPaciente: 295751)
		
		def jsonDetalle = new groovy.json.JsonBuilder()
		jsonDetalle.call(
		[
			{
				cveArt 12
				cantidad 1				
			},
		
			{
				cveArt 13
				cantidad 2
			},
		
			{
				cveArt 14
				cantidad 3
			},
		
			{	
				cveArt 15
				cantidad 4
			}
			
		])		
			
		def seguimientoHosp = medicamentoService.guardarSeguimientoHosp(JSON.parse(jsonPadre.toPrettyString()),Usuario.read(6558))
		
		for(index in 0..3){
			medicamentoService.guardarMedicamento(JSON.parse(jsonDetalle.toPrettyString())[index],seguimientoHosp)
		}
		
		return seguimientoHosp
	}

    @Test
    void testListarArticulo(){	
		mc.params.term="KETOROLACO"
		mc.listarArticulo()
		def json = JSON.parse(mc.response.contentAsString)
		assertEquals(4,json.size())
		println mc.response.contentAsString;			        
    }	
	
	@Test
	void testBuscaArticulo(){		
		mc.params.id=15
		mc.buscarArticulo()
		def jsonArticulo = JSON.parse(mc.response.contentAsString)
				
		assertNotNull(jsonArticulo)
		println jsonArticulo;		
		assertEquals(1.8886, jsonArticulo.precioCierre,0)			
	}
		
	@Test
	void "guardar Seguimiento con 11 Medicamentos"(){	
		
		mc.params.idSeguimiento = null
		
		def jsonPadre = new groovy.json.JsonBuilder() 
		jsonPadre(
			idAdmision: 178472, 
			idPaciente: 295751)
		
		def jsonDetalle = new groovy.json.JsonBuilder()
		jsonDetalle.call(
		[
			{
				cveArt 12
				cantidad 10
				costo 15.25
			},
		
			{
				cveArt 12
				cantidad 10
				costo 15.25
			}
			
		])	
		
		println jsonPadre.toPrettyString()
		println jsonDetalle.toPrettyString()
		
		
		mc.params.dataPadre=jsonPadre.toPrettyString()
		mc.params.dataDetalle=jsonDetalle.toPrettyString()
		
		mc.guardar()
		
		def jsonResult = JSON.parse(mc.response.contentAsString)
		
		assertNotNull(jsonResult.idSeguimiento)
		
		for(index in 1..10){			
			mc.params.idSeguimiento = jsonResult.idSeguimiento			
			mc.params.dataDetalle=jsonDetalle.toPrettyString()			
			mc.guardar()			
		}	
	}
	
	@Test
	void "consultar Detalle Medicamento"(){
		
		def seguimientoHosp = crearEscenario();
		
		mc.params.idSeguimiento = seguimientoHosp.id
		mc.params.rows = 10
		mc.params.page = 1
		
		mc.consultarDetalle()
		
		def jsonResult = JSON.parse(mc.response.contentAsString)
		
		println jsonResult
	}
	
	
	
	
			

	
}
