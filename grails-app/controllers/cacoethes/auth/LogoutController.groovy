package cacoethes.auth

class LogoutController {
    String logoutUrl

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index() {
        // TODO put any pre-logout code here
        redirect uri: logoutUrl
    }
}
