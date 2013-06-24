grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    inherits "global"
    log      "warn"
    checksums true

    repositories {
        inherits = true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://maven.springframework.org/milestone/"
        mavenLocal()
    }
    dependencies {
        runtime "mysql:mysql-connector-java:5.1.17", "com.google.inject:guice:3.0"

    }

    plugins {
        compile ":heroku:1.0.1", ":cloud-support:1.0.8", {
            exclude "database-session"
        }

        compile ":hibernate:$grailsVersion",
                ":spring-security-core:1.2.7.3",
                ":spring-security-oauth:2.0.2",
                ":spring-security-openid:1.0.4", {
            excludes "guice", "spock", "spock-grails-support"
        }

        runtime ":resources:1.2-RC1",
                ":jquery:1.8.0",
//                ":cloud-foundry:1.2.3",
                ":database-migration:1.1",
                ":markdown:1.0.0.RC1",
                ":modernizr:2.6.2",
                ":sendgrid:0.4",
                ":webxml:1.4.1"

        build ":tomcat:$grailsVersion"
    }
}
