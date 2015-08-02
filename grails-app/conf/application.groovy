grails.config.locations = ["file:./local-config.groovy"]

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
try {
    environments {
        production {
            dataSource {
                dbCreate = "update"
                driverClassName = "org.postgresql.Driver"
                dialect = "org.hibernate.dialect.PostgreSQL9Dialect"

                final uri = new URI(System.getenv("DATABASE_URL") ?: "postgres://pledbrook:password@localhost:5432/ggx_talks")
                url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path
                username = uri.userInfo.split(/:/)[0]
                password = uri.userInfo.split(/:/)[1]
            }
        }
    }
}
catch (Throwable t) {
    println "!!!!!!! Thrown: ${t.message} (${t.getClass()})"
}

// Load configuration from JSON provided in the optional GRAILS_APP_CONFIG env variable.
ConfigLoader.addEntries(loadJson(fetchJson()), this)
def fetchJson() { return System.getenv("GRAILS_APP_CONFIG") }
def loadJson(content) { return content ? grails.converters.JSON.parse(content) : [:] }
