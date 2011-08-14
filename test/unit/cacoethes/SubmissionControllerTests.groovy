package cacoethes



import org.junit.*
import grails.test.mixin.*
import javax.servlet.http.HttpServletResponse

@TestFor(SubmissionController)
@Mock(Submission)
class SubmissionControllerTests {

    void testIndex() {
        controller.index()
        assert "/submission/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.submissionInstanceList.size() == 0
        assert model.submissionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.submissionInstance != null
    }

    void testSave() {
        controller.save()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.save()

        assert model.submissionInstance != null
        assert view == '/submission/create'

        response.reset()

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/submission/show/1'
        assert controller.flash.message != null
        assert Submission.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'


        def submission = new Submission()

        // TODO: populate domain properties

        assert submission.save() != null

        params.id = submission.id

        def model = controller.show()

        assert model.submissionInstance == submission
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'


        def submission = new Submission()

        // TODO: populate valid domain properties

        assert submission.save() != null

        params.id = submission.id

        def model = controller.edit()

        assert model.submissionInstance == submission
    }

    void testUpdate() {

        controller.update()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'

        response.reset()


        def submission = new Submission()

        // TODO: populate valid domain properties

        assert submission.save() != null

        // test invalid parameters in update
        params.id = submission.id

        controller.update()

        assert view == "/submission/edit"
        assert model.submissionInstance != null

        submission.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/submission/show/$submission.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'

        response.reset()

        def submission = new Submission()

        // TODO: populate valid domain properties
        assert submission.save() != null
        assert Submission.count() == 1

        params.id = submission.id

        controller.delete()

        assert Submission.count() == 0
        assert Submission.get(submission.id) == null
        assert response.redirectedUrl == '/submission/list'
    }
}
