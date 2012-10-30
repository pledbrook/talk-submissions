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

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
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

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
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
