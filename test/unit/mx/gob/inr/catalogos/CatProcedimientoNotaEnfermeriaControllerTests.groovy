package mx.gob.inr.catalogos



import org.junit.*
import grails.test.mixin.*

@TestFor(CatProcedimientoNotaEnfermeriaController)
@Mock(CatProcedimientoNotaEnfermeria)
class CatProcedimientoNotaEnfermeriaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/catProcedimientoNotaEnfermeria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.catProcedimientoNotaEnfermeriaInstanceList.size() == 0
        assert model.catProcedimientoNotaEnfermeriaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.catProcedimientoNotaEnfermeriaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.catProcedimientoNotaEnfermeriaInstance != null
        assert view == '/catProcedimientoNotaEnfermeria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/show/1'
        assert controller.flash.message != null
        assert CatProcedimientoNotaEnfermeria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/list'

        populateValidParams(params)
        def catProcedimientoNotaEnfermeria = new CatProcedimientoNotaEnfermeria(params)

        assert catProcedimientoNotaEnfermeria.save() != null

        params.id = catProcedimientoNotaEnfermeria.id

        def model = controller.show()

        assert model.catProcedimientoNotaEnfermeriaInstance == catProcedimientoNotaEnfermeria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/list'

        populateValidParams(params)
        def catProcedimientoNotaEnfermeria = new CatProcedimientoNotaEnfermeria(params)

        assert catProcedimientoNotaEnfermeria.save() != null

        params.id = catProcedimientoNotaEnfermeria.id

        def model = controller.edit()

        assert model.catProcedimientoNotaEnfermeriaInstance == catProcedimientoNotaEnfermeria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catProcedimientoNotaEnfermeria = new CatProcedimientoNotaEnfermeria(params)

        assert catProcedimientoNotaEnfermeria.save() != null

        // test invalid parameters in update
        params.id = catProcedimientoNotaEnfermeria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/catProcedimientoNotaEnfermeria/edit"
        assert model.catProcedimientoNotaEnfermeriaInstance != null

        catProcedimientoNotaEnfermeria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/catProcedimientoNotaEnfermeria/show/$catProcedimientoNotaEnfermeria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        catProcedimientoNotaEnfermeria.clearErrors()

        populateValidParams(params)
        params.id = catProcedimientoNotaEnfermeria.id
        params.version = -1
        controller.update()

        assert view == "/catProcedimientoNotaEnfermeria/edit"
        assert model.catProcedimientoNotaEnfermeriaInstance != null
        assert model.catProcedimientoNotaEnfermeriaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catProcedimientoNotaEnfermeria = new CatProcedimientoNotaEnfermeria(params)

        assert catProcedimientoNotaEnfermeria.save() != null
        assert CatProcedimientoNotaEnfermeria.count() == 1

        params.id = catProcedimientoNotaEnfermeria.id

        controller.delete()

        assert CatProcedimientoNotaEnfermeria.count() == 0
        assert CatProcedimientoNotaEnfermeria.get(catProcedimientoNotaEnfermeria.id) == null
        assert response.redirectedUrl == '/catProcedimientoNotaEnfermeria/list'
    }
}
