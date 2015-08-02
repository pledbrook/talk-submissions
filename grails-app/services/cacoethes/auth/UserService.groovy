package cacoethes.auth

import grails.databinding.SimpleMapDataBindingSource
import grails.transaction.Transactional
import org.pac4j.oauth.profile.twitter.TwitterProfile
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder as SCH

@Transactional
class UserService {
    def passwordEncoder
    def grailsWebDataBinder

    User currentUser() {
        log.info "Fetching current user"
        if (SCH.context.authentication instanceof ClientAuthenticationToken) {
            def userId = SCH.context.authentication.userProfile.getAttribute(GormProfileCreator.PERSISTENCE_ID_KEY)
            log.debug "Found user ID ${userId} attached to current user's auth profile"
            return User.get(userId)
        }
        else return null
    }

    /**
     * Returns the Twitter username for the current user. This will return
     * {@code null} if the user is not logged in via Twitter.
     */
    String currentTwitterUsername() {
        log.info "Fetching Twitter username for current user"
        if (SCH.context.authentication instanceof ClientAuthenticationToken) {
            def userProfile = SCH.context.authentication.userProfile
            if (userProfile instanceof TwitterProfile) {
                log.debug "Found Twitter username ${userProfile.username} for current user"
                return userProfile.username

            }
            else {
                log.debug "User is not logged in via Twitter"
                return null
            }
        }
        else {
            log.debug "No user is logged in"
            return null
        }
    }

    User createUser(String username, String password = null) {
        def u = new User(username: username)
        if (password) u.password = passwordEncoder.encode(password)
        else u.password = "*******"

        return u
    }

    User createUser(Map params, String username, String password = null) {
        def u = createUser(username, password)
        grailsWebDataBinder.bind(u, new SimpleMapDataBindingSource(params))
        return u
    }
}
