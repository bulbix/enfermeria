package mx.gob.inr.hojaRegistroClinico



import org.junit.*
import grails.test.mixin.*

@TestFor(HojaRegistroEnfermeriaController)
@Mock(HojaRegistroEnfermeria)
class HojaRegistroEnfermeriaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/hojaRegistroEnfermeria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.hojaRegistroEnfermeriaInstanceList.size() == 0
        assert model.hojaRegistroEnfermeriaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.hojaRegistroEnfermeriaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.hojaRegistroEnfermeriaInstance != null
        assert view == '/hojaRegistroEnfermeria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/hojaRegistroEnfermeria/show/1'
        assert controller.flash.message != null
        assert HojaRegistroEnfermeria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/hojaRegistroEnfermeria/list'

        populateValidParams(params)
        def hojaRegistroEnfermeria = new HojaRegistroEnfermeria(params)

        assert hojaRegistroEnfermeria.save() != null

        params.id = hojaRegistroEnfermeria.id

        def model = controller.show()

        assert model.hojaRegistroEnfermeriaInstance == hojaRegistroEnfermeria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/hojaRegistroEnfermeria/list'

        populateValidParams(params)
        def hojaRegistroEnfermeria = new HojaRegistroEnfermeria(params)

        assert hojaRegistroEnfermeria.save() != null

        params.id = hojaRegistroEnfermeria.id

        def model = controller.edit()

        assert model.hojaRegistroEnfermeriaInstance == hojaRegistroEnfermeria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/hojaRegistroEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def hojaRegistroEnfermeria = new HojaRegistroEnfermeria(params)

        assert hojaRegistroEnfermeria.save() != null

        // test invalid parameters in update
        params.id = hojaRegistroEnfermeria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/hojaRegistroEnfermeria/edit"
        assert model.hojaRegistroEnfermeriaInstance != null

        hojaRegistroEnfermeria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/hojaRegistroEnfermeria/show/$hojaRegistroEnfermeria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        hojaRegistroEnfermeria.clearErrors()

        populateValidParams(params)
        params.id = hojaRegistroEnfermeria.id
        params.version = -1
        controller.update()

        assert view == "/hojaRegistroEnfermeria/edit"
        assert model.hojaRegistroEnfermeriaInstance != null
        assert model.hojaRegistroEnfermeriaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/hojaRegistroEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def hojaRegistroEnfermeria = new HojaRegistroEnfermeria(params)

        assert hojaRegistroEnfermeria.save() != null
        assert HojaRegistroEnfermeria.count() == 1

        params.id = hojaRegistroEnfermeria.id

        controller.delete()

        assert HojaRegistroEnfermeria.count() == 0
        assert HojaRegistroEnfermeria.get(hojaRegistroEnfermeria.id) == null
        assert response.redirectedUrl == '/hojaRegistroEnfermeria/list'
    }
}
