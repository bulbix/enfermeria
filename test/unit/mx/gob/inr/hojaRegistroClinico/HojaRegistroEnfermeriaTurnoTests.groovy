package mx.gob.inr.hojaRegistroClinico



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(HojaRegistroEnfermeriaTurno)
class HojaRegistroEnfermeriaTurnoTests {

    void testSomething() {
       def turno = HojaRegistroEnfermeriaTurno.Turno("MATUTINO")
	   assertEquals(turno.ordinal, 1)
    }
}
