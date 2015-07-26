import cacoethes.auth.CombinedUserDetailsService
import cacoethes.auth.GormAuthenticator
import cacoethes.auth.GormProfileCreator
import cacoethes.config.SecurityConfiguration
import org.pac4j.core.client.Clients
import org.pac4j.http.client.FormClient
import org.pac4j.oauth.client.Google2Client
import org.pac4j.oauth.client.TwitterClient
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider
import org.pac4j.springframework.security.web.ClientAuthenticationFilter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// Place your Spring DSL code here
beans = {
    webSecurityConfiguration(SecurityConfiguration)
    passwordEncoder(BCryptPasswordEncoder)

    // PAC4j configuration
    twitterClient(TwitterClient) {
        key = application.config.app.twitterclient.key
        secret = application.config.app.twitterclient.secret
    }

    googleClient(Google2Client) {
        key = application.config.app.googleclient.key
        secret = application.config.app.googleclient.secret
    }

    formClient(FormClient) {
        loginUrl = "http://localhost:8080/login/auth"
        authenticator = ref("usernamePasswordAuthenticator")
        profileCreator = ref("usernameProfileCreator")
    }

    usernamePasswordAuthenticator(GormAuthenticator) {
        passwordEncoder = ref("passwordEncoder")
    }

    usernameProfileCreator(GormProfileCreator)

    pacClients(Clients) {
        callbackUrl = "http://localhost:8080/callback"
        clients = [ref("twitterClient"), ref("googleClient"), ref("formClient")]
    }

    clientFilter(ClientAuthenticationFilter, "/callback") {
        clients = ref("pacClients")
        authenticationManager = ref("authenticationManager")
    }

    clientProvider(ClientAuthenticationProvider) {
        clients = ref("pacClients")
        userDetailsService = ref("userDetailsService")
    }

    userDetailsService(CombinedUserDetailsService)
}
