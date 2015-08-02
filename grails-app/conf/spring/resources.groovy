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
    def rootUrl = System.getenv("ROOT_URL") ?: "http://localhost:8080"

    webSecurityConfiguration(SecurityConfiguration)
    passwordEncoder(BCryptPasswordEncoder)

    // PAC4j configuration
    twitterClient(TwitterClient) {
        key = System.getenv("APP_TWITTERCLIENT_KEY")
        secret = System.getenv("APP_TWITTERCLIENT_SECRET")
    }

    googleClient(Google2Client) {
        key = System.getenv("APP_GOOGLECLIENT_KEY")
        secret = System.getenv("APP_GOOGLECLIENT_SECRET")
    }

    formClient(FormClient) {
        loginUrl = "${rootUrl}/login/auth"
        authenticator = ref("usernamePasswordAuthenticator")
        profileCreator = ref("usernameProfileCreator")
    }

    usernamePasswordAuthenticator(GormAuthenticator) {
        passwordEncoder = ref("passwordEncoder")
    }

    usernameProfileCreator(GormProfileCreator)

    pacClients(Clients) {
        callbackUrl = "${rootUrl}/callback"
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
