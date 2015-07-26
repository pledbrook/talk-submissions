package cacoethes.auth

import grails.transaction.Transactional
import org.pac4j.core.exception.CredentialsException
import org.pac4j.http.credentials.UsernamePasswordAuthenticator
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Created by pledbrook on 25/07/15.
 */
class GormAuthenticator implements UsernamePasswordAuthenticator {
    private static final Logger log = LoggerFactory.getLogger(this)

    PasswordEncoder passwordEncoder

    @Override
    @Transactional
    void validate(UsernamePasswordCredentials usernamePasswordCredentials) {
        log.debug "Authenticating password for user '${usernamePasswordCredentials.username}'"

        def user = User.where { username == usernamePasswordCredentials.username }.get()

        if (!user) {
            log.info "Unknown user '${usernamePasswordCredentials.username}'"
            throw new CredentialsException("Unknown user")
        }

        if (passwordEncoder.matches(usernamePasswordCredentials.password, user.password)) {
            log.info "User '${usernamePasswordCredentials.username}' logged in successfully"
        }
        else {
            log.info "Invalid username or password for user '${usernamePasswordCredentials.username}'"
            throw new CredentialsException("Invalid username or password")
        }
    }
}
