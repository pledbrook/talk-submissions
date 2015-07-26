import grails.util.Environment

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

/*
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']
*/

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
    }
}

oauth {
    providers {
        twitter {
            api = org.scribe.builder.api.TwitterApi
            successUri = '/oauth/success?provider=twitter'
            failureUri = '/oauth/failure'
            key = '*******'
            secret = '*******'
            callback = "${grails.serverURL}/oauth/twitter/callback"
        }
        facebook {
            api = org.scribe.builder.api.FacebookApi
            successUri = '/oauth/success?provider=facebook'
            failureUri = '/oauth/failure'
            key = '*******'
            secret = '*******'
            callback = "${grails.serverURL}/oauth/facebook/callback"
        }
        google {
            api = org.scribe.builder.api.GoogleApi
            successUri = '/oauth/success?provider=google'
            failureUri = '/oauth/failure'
            scope = 'https://www.googleapis.com/auth/userinfo.email'
            key = '*******'
            secret = '*******'
            callback = "${grails.serverURL}/oauth/google/callback"
        }
    }
}

sendgrid {
    username = '*******'
    password = '********'
}

grails.mail.default.from = "admin@nowhere.net"

// Enable database migrations on startup.
if (Environment.current == Environment.PRODUCTION) {
//    grails.plugin.databasemigration.updateOnStart = true
//    grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]
}

// Enable dbconsole so we can execute arbitrary SQL against the database.
grails.dbconsole.enabled = true
grails.resources.adhoc.patterns = ["/images/*", "/css/*", "*.js"]

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'cacoethes.auth.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'cacoethes.auth.UserRole'
grails.plugins.springsecurity.authority.className = 'cacoethes.auth.Role'
grails.plugins.springsecurity.rememberMe.persistent = true
grails.plugins.springsecurity.rememberMe.persistentToken.domainClassName = 'cacoethes.auth.PersistentLogin'
grails.plugins.springsecurity.openid.domainClass = 'cacoethes.auth.OpenId'
grails.plugins.springsecurity.openid.registration.roleNames = ['ROLE_USER']

grails.plugins.springsecurity.controllerAnnotations.staticRules = [
   '/dbconsole/**': ['ROLE_ADMIN']
]

// Load configuration from JSON provided in the optional GRAILS_APP_CONFIG env variable.
ConfigLoader.addEntries(loadJson(fetchJson()), this)
def fetchJson() { return System.getenv("GRAILS_APP_CONFIG") }
def loadJson(content) { return content ? grails.converters.JSON.parse(content) : [:] }
