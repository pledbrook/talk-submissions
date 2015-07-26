package cacoethes.auth

//import org.pac4j.springframework.security.authentication.ClientAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder

class SecurityInterceptor {
    def userService

    SecurityInterceptor() {
        matchAll()
    }

//    boolean before() { return true }

    boolean after() {
        if (model && !model["currentUser"]) {
            model["currentUser"] = userService.currentUser()
        }
        return true
    }

    void afterView() { }

//    private getAuthentication() {
//        return SecurityContextHolder.context.authentication
//    }

}
