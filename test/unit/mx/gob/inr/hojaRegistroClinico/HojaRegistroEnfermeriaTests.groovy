package mx.gob.inr.hojaRegistroClinico



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(HojaRegistroEnfermeria)
class HojaRegistroEnfermeriaTests {

    void testConsultarHoja() {
       service.consultarHoja(868)
    }
}
