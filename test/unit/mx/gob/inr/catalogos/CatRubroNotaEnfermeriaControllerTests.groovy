package mx.gob.inr.catalogos



import org.junit.*
import grails.test.mixin.*

@TestFor(CatRubroNotaEnfermeriaController)
@Mock(CatRubroNotaEnfermeria)
class CatRubroNotaEnfermeriaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/catRubroNotaEnfermeria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.catRubroNotaEnfermeriaInstanceList.size() == 0
        assert model.catRubroNotaEnfermeriaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.catRubroNotaEnfermeriaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.catRubroNotaEnfermeriaInstance != null
        assert view == '/catRubroNotaEnfermeria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/catRubroNotaEnfermeria/show/1'
        assert controller.flash.message != null
        assert CatRubroNotaEnfermeria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/catRubroNotaEnfermeria/list'

        populateValidParams(params)
        def catRubroNotaEnfermeria = new CatRubroNotaEnfermeria(params)

        assert catRubroNotaEnfermeria.save() != null

        params.id = catRubroNotaEnfermeria.id

        def model = controller.show()

        assert model.catRubroNotaEnfermeriaInstance == catRubroNotaEnfermeria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/catRubroNotaEnfermeria/list'

        populateValidParams(params)
        def catRubroNotaEnfermeria = new CatRubroNotaEnfermeria(params)

        assert catRubroNotaEnfermeria.save() != null

        params.id = catRubroNotaEnfermeria.id

        def model = controller.edit()

        assert model.catRubroNotaEnfermeriaInstance == catRubroNotaEnfermeria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/catRubroNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catRubroNotaEnfermeria = new CatRubroNotaEnfermeria(params)

        assert catRubroNotaEnfermeria.save() != null

        // test invalid parameters in update
        params.id = catRubroNotaEnfermeria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/catRubroNotaEnfermeria/edit"
        assert model.catRubroNotaEnfermeriaInstance != null

        catRubroNotaEnfermeria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/catRubroNotaEnfermeria/show/$catRubroNotaEnfermeria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        catRubroNotaEnfermeria.clearErrors()

        populateValidParams(params)
        params.id = catRubroNotaEnfermeria.id
        params.version = -1
        controller.update()

        assert view == "/catRubroNotaEnfermeria/edit"
        assert model.catRubroNotaEnfermeriaInstance != null
        assert model.catRubroNotaEnfermeriaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/catRubroNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catRubroNotaEnfermeria = new CatRubroNotaEnfermeria(params)

        assert catRubroNotaEnfermeria.save() != null
        assert CatRubroNotaEnfermeria.count() == 1

        params.id = catRubroNotaEnfermeria.id

        controller.delete()

        assert CatRubroNotaEnfermeria.count() == 0
        assert CatRubroNotaEnfermeria.get(catRubroNotaEnfermeria.id) == null
        assert response.redirectedUrl == '/catRubroNotaEnfermeria/list'
    }
}
