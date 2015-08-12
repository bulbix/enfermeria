package mx.gob.inr.catalogos



import org.junit.*
import grails.test.mixin.*

@TestFor(CatNotaEnfermeriaController)
@Mock(CatNotaEnfermeria)
class CatNotaEnfermeriaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/catNotaEnfermeria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.catNotaEnfermeriaInstanceList.size() == 0
        assert model.catNotaEnfermeriaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.catNotaEnfermeriaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.catNotaEnfermeriaInstance != null
        assert view == '/catNotaEnfermeria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/catNotaEnfermeria/show/1'
        assert controller.flash.message != null
        assert CatNotaEnfermeria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/catNotaEnfermeria/list'

        populateValidParams(params)
        def catNotaEnfermeria = new CatNotaEnfermeria(params)

        assert catNotaEnfermeria.save() != null

        params.id = catNotaEnfermeria.id

        def model = controller.show()

        assert model.catNotaEnfermeriaInstance == catNotaEnfermeria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/catNotaEnfermeria/list'

        populateValidParams(params)
        def catNotaEnfermeria = new CatNotaEnfermeria(params)

        assert catNotaEnfermeria.save() != null

        params.id = catNotaEnfermeria.id

        def model = controller.edit()

        assert model.catNotaEnfermeriaInstance == catNotaEnfermeria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/catNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catNotaEnfermeria = new CatNotaEnfermeria(params)

        assert catNotaEnfermeria.save() != null

        // test invalid parameters in update
        params.id = catNotaEnfermeria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/catNotaEnfermeria/edit"
        assert model.catNotaEnfermeriaInstance != null

        catNotaEnfermeria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/catNotaEnfermeria/show/$catNotaEnfermeria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        catNotaEnfermeria.clearErrors()

        populateValidParams(params)
        params.id = catNotaEnfermeria.id
        params.version = -1
        controller.update()

        assert view == "/catNotaEnfermeria/edit"
        assert model.catNotaEnfermeriaInstance != null
        assert model.catNotaEnfermeriaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/catNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catNotaEnfermeria = new CatNotaEnfermeria(params)

        assert catNotaEnfermeria.save() != null
        assert CatNotaEnfermeria.count() == 1

        params.id = catNotaEnfermeria.id

        controller.delete()

        assert CatNotaEnfermeria.count() == 0
        assert CatNotaEnfermeria.get(catNotaEnfermeria.id) == null
        assert response.redirectedUrl == '/catNotaEnfermeria/list'
    }
}
