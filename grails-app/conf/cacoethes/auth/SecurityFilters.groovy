package cacoethes.auth

import static cacoethes.auth.SpringSecurityOAuthController.SPRING_SECURITY_OAUTH_TOKEN
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler as OIAFH
import org.springframework.security.core.context.SecurityContextHolder

class SecurityFilters {
    def springSecurityService

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                def grailsUser = SecurityContextHolder.context.authentication?.principal
                def oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]

                if (grailsUser instanceof GrailsUser && oAuthToken) {
                    User.withTransaction { status ->
                        def user = User.get(grailsUser.id)
                        if (user) {
                            user.addToOauthIds(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                            if (!user.save()) {
                                status.setRollbackOnly()
                            }
                            else {
                                authenticateOpenId session
                            }
                        }

                    }

                    session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
                }
            }
            after = { Map model ->
                if (model && !model.currentUser) {
                    model.currentUser = springSecurityService.currentUser
                }
            }
            afterView = { Exception e ->

            }
        }
    }

    private void authenticateOpenId(session) {
        def username = session.removeAttribute OIAFH.LAST_OPENID_USERNAME
        session.removeAttribute OIAFH.LAST_OPENID_ATTRIBUTES

        if (username) springSecurityService.reauthenticate username
    }
}
