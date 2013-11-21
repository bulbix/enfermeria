package enfermeria

import static org.junit.Assert.*
import mx.gob.inr.hojaRegistroClinico.HojaRegistroClinicoService
import org.junit.*

class HojaRegistroEnfermeriaTests {
	
	HojaRegistroClinicoService hojaRegistroClinicoService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test(expected = IllegalArgumentException.class)
    void testConsultarHoja() {
		def hoja = hojaRegistroClinicoService.consultarHoja(859)
        assertNotNull(hoja)
		assertEquals(859,hoja.id )
		
		
    }
}
