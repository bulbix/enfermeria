package enfermeria

import static org.junit.Assert.*
import mx.gob.inr.utils.UtilService
import org.junit.*


class UtilTests {
		
	
	UtilService utilService;
	
	@Test
	void testPisos(){
		//log.info("Estoy en la prueba")
		def pisos = utilService.consultarPisos()
		assertEquals(14, pisos.size())
	}
	
	@Test
	void testServicios(){
		//log.info("Estoy en la prueba")
		def servicios = utilService.consultarServicios((long)895)
		assertEquals(14, servicios.size())
	}
	
	@Test
	void TestPacientes(){
		def pacientes = utilService.consultarPacientes("GONZALEZ")
		assertEquals(14, pacientes.size())
	}
	
	

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }
	
}
