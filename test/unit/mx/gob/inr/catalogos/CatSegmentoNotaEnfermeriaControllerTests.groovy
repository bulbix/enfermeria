package mx.gob.inr.catalogos



import org.junit.*
import grails.test.mixin.*

@TestFor(CatSegmentoNotaEnfermeriaController)
@Mock(CatSegmentoNotaEnfermeria)
class CatSegmentoNotaEnfermeriaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/catSegmentoNotaEnfermeria/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.catSegmentoNotaEnfermeriaInstanceList.size() == 0
        assert model.catSegmentoNotaEnfermeriaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.catSegmentoNotaEnfermeriaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.catSegmentoNotaEnfermeriaInstance != null
        assert view == '/catSegmentoNotaEnfermeria/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/show/1'
        assert controller.flash.message != null
        assert CatSegmentoNotaEnfermeria.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/list'

        populateValidParams(params)
        def catSegmentoNotaEnfermeria = new CatSegmentoNotaEnfermeria(params)

        assert catSegmentoNotaEnfermeria.save() != null

        params.id = catSegmentoNotaEnfermeria.id

        def model = controller.show()

        assert model.catSegmentoNotaEnfermeriaInstance == catSegmentoNotaEnfermeria
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/list'

        populateValidParams(params)
        def catSegmentoNotaEnfermeria = new CatSegmentoNotaEnfermeria(params)

        assert catSegmentoNotaEnfermeria.save() != null

        params.id = catSegmentoNotaEnfermeria.id

        def model = controller.edit()

        assert model.catSegmentoNotaEnfermeriaInstance == catSegmentoNotaEnfermeria
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catSegmentoNotaEnfermeria = new CatSegmentoNotaEnfermeria(params)

        assert catSegmentoNotaEnfermeria.save() != null

        // test invalid parameters in update
        params.id = catSegmentoNotaEnfermeria.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/catSegmentoNotaEnfermeria/edit"
        assert model.catSegmentoNotaEnfermeriaInstance != null

        catSegmentoNotaEnfermeria.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/catSegmentoNotaEnfermeria/show/$catSegmentoNotaEnfermeria.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        catSegmentoNotaEnfermeria.clearErrors()

        populateValidParams(params)
        params.id = catSegmentoNotaEnfermeria.id
        params.version = -1
        controller.update()

        assert view == "/catSegmentoNotaEnfermeria/edit"
        assert model.catSegmentoNotaEnfermeriaInstance != null
        assert model.catSegmentoNotaEnfermeriaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/list'

        response.reset()

        populateValidParams(params)
        def catSegmentoNotaEnfermeria = new CatSegmentoNotaEnfermeria(params)

        assert catSegmentoNotaEnfermeria.save() != null
        assert CatSegmentoNotaEnfermeria.count() == 1

        params.id = catSegmentoNotaEnfermeria.id

        controller.delete()

        assert CatSegmentoNotaEnfermeria.count() == 0
        assert CatSegmentoNotaEnfermeria.get(catSegmentoNotaEnfermeria.id) == null
        assert response.redirectedUrl == '/catSegmentoNotaEnfermeria/list'
    }
}
