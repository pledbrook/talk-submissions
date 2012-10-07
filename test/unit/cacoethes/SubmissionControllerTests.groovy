package cacoethes



import org.junit.*
import grails.test.mixin.*

/**
 * SubmissionControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(SubmissionController)
@Mock(Submission)
class SubmissionControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

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

        assert model.submissionInstance != null
        assert view == '/submission/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/submission/show/1'
        assert controller.flash.message != null
        assert Submission.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'


        populateValidParams(params)
        def submission = new Submission(params)

        assert submission.save() != null

        params.id = submission.id

        def model = controller.show()

        assert model.submissionInstance == submission
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'


        populateValidParams(params)
        def submission = new Submission(params)

        assert submission.save() != null

        params.id = submission.id

        def model = controller.edit()

        assert model.submissionInstance == submission
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'

        response.reset()


        populateValidParams(params)
        def submission = new Submission(params)

        assert submission.save() != null

        // test invalid parameters in update
        params.id = submission.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/submission/edit"
        assert model.submissionInstance != null

        submission.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/submission/show/$submission.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        submission.clearErrors()

        populateValidParams(params)
        params.id = submission.id
        params.version = -1
        controller.update()

        assert view == "/submission/edit"
        assert model.submissionInstance != null
        assert model.submissionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/submission/list'

        response.reset()

        populateValidParams(params)
        def submission = new Submission(params)

        assert submission.save() != null
        assert Submission.count() == 1

        params.id = submission.id

        controller.delete()

        assert Submission.count() == 0
        assert Submission.get(submission.id) == null
        assert response.redirectedUrl == '/submission/list'
    }
}
