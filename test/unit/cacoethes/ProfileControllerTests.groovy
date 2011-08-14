package cacoethes



import org.junit.*
import grails.test.mixin.*
import javax.servlet.http.HttpServletResponse

@TestFor(ProfileController)
@Mock(Profile)
class ProfileControllerTests {

    void testIndex() {
        controller.index()
        assert "/profile/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.profileInstanceList.size() == 0
        assert model.profileInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.profileInstance != null
    }

    void testSave() {
        controller.save()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.save()

        assert model.profileInstance != null
        assert view == '/profile/create'

        response.reset()

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/profile/show/1'
        assert controller.flash.message != null
        assert Profile.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/profile/list'


        def profile = new Profile()

        // TODO: populate domain properties

        assert profile.save() != null

        params.id = profile.id

        def model = controller.show()

        assert model.profileInstance == profile
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/profile/list'


        def profile = new Profile()

        // TODO: populate valid domain properties

        assert profile.save() != null

        params.id = profile.id

        def model = controller.edit()

        assert model.profileInstance == profile
    }

    void testUpdate() {

        controller.update()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/profile/list'

        response.reset()


        def profile = new Profile()

        // TODO: populate valid domain properties

        assert profile.save() != null

        // test invalid parameters in update
        params.id = profile.id

        controller.update()

        assert view == "/profile/edit"
        assert model.profileInstance != null

        profile.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/profile/show/$profile.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/profile/list'

        response.reset()

        def profile = new Profile()

        // TODO: populate valid domain properties
        assert profile.save() != null
        assert Profile.count() == 1

        params.id = profile.id

        controller.delete()

        assert Profile.count() == 0
        assert Profile.get(profile.id) == null
        assert response.redirectedUrl == '/profile/list'
    }
}
