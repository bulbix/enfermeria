package mx.gob.inr



import grails.test.mixin.*
import mx.gob.inr.hojaRegistroClinico.HojaRegistroClinicoController;

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HojaRegistroClinicoController)
class HojaRegistroClinicoControllerTests {

    void testSomething() {
       controller.index()
	   assert response.text == ['dedod']
    }
}
