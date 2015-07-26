package cacoethes.auth

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

//    void afterView() { }
}
