app {
    security {
        defaultTargetUrl = "/"
    }
}

beans {
    'cacoethes.auth.LoginController' {
        defaultTarget = app.security.defaultTargetUrl
        securityCheckUrl = "/j_spring_security_check"
    }

    'cacoethes.auth.LogoutController' {
        logoutUrl = "/j_spring_security_logout"
    }
}

// Load configuration from JSON provided in the optional GRAILS_APP_CONFIG env variable.
ConfigLoader.addEntries(loadJson(fetchJson()), this)
def fetchJson() { return System.getenv("GRAILS_APP_CONFIG") }
def loadJson(content) { return content ? grails.converters.JSON.parse(content) : [:] }
