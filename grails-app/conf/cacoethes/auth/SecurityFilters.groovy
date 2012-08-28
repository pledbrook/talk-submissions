package cacoethes.auth

import static cacoethes.auth.SpringSecurityOAuthController.SPRING_SECURITY_OAUTH_TOKEN
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.context.SecurityContextHolder

class SecurityFilters {

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
                        }

                    }

                    session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
                    SecurityContextHolder.context.authentication = oAuthToken
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
