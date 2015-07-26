package cacoethes.auth

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.pac4j.core.context.J2EContext
import org.pac4j.oauth.client.Google2Client
import org.pac4j.oauth.client.TwitterClient

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class LoginController {
    String ajaxLoginUrl = "/ajax/auth"
    String defaultTarget = "/"
    String securityCheckUrl = "/j_spring_security_check"

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    def twitterClient
    def googleClient
    def formClient

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index() {
        if (isLoggedIn()) {
            redirect uri: defaultTarget
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth() {

        if (request.remoteUser != null) {
            redirect uri: defaultTarget
            return
        }

        def pacContext = new J2EContext(request, response)

        return [
            postUrl: formClient.callbackUrl,
            twitterUrl: twitterClient.getRedirectAction(pacContext, false, false).location,
            googleUrl: googleClient.getRedirectAction(pacContext, false, false).location]
    }

    /**
     * The redirect action for Ajax requests. 
     */
    def authAjax() {
        response.setHeader 'Location', ajaxLoginUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied() {
        if (isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full() {
        render view: 'auth', params: params,
            model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                    postUrl: "${request.contextPath}${securityCheckUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail() {

        def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        /*
        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
        */
            flash.message = msg
            redirect action: 'auth', params: params
//        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess() {
        render([success: true, username: request.remoteUser] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied() {
        render([error: 'access denied'] as JSON)
    }

    private boolean isLoggedIn() {
        return request.remoteUser != null
    }
}
