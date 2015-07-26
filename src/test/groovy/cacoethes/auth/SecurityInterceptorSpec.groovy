package cacoethes.auth


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SecurityInterceptor)
class SecurityInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test security interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"security")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
