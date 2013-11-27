package mx.gob.inr.seguimientoHosp

import static org.junit.Assert.*
import org.junit.*

class CirugiaTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testNota() {
        def nota = Notaquirposopera.read(32147)
		assertNotNull(nota)
		assertEquals(nota.operacionesPracticadas.size(), 1)
    }
}
