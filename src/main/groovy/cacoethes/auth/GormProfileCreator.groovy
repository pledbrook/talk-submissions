package cacoethes.auth

import grails.transaction.Transactional
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.ProfileCreator
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.pac4j.http.profile.HttpProfile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by pledbrook on 25/07/15.
 */
class GormProfileCreator implements ProfileCreator<UsernamePasswordCredentials, HttpProfile> {
    static final String PERSISTENCE_ID_KEY = "persistence_id"

    private static final Logger log = LoggerFactory.getLogger(this)

    @Override
    @Transactional
    HttpProfile create(UsernamePasswordCredentials usernamePasswordCredentials) {
        log.debug "Creating auth profile for user '${usernamePasswordCredentials.username}'"

        def user = User.where { username == usernamePasswordCredentials.username }.get()

        def profile = new HttpProfile()
        profile.id = user.username
        profile.addAttribute(CommonProfile.USERNAME, user.username)
        if (user.profile) {
            log.debug "Found database profile for user '${user.username}'"
            profile.addAttributes(display_name: user.profile.name, email: user.profile.email)
        }

        for (r in user.authorities) {
            profile.addRole(r.authority)
        }

        log.info "Created auth profile for user '${user.username}' with ID ${user.id}"
        return profile
    }
}
