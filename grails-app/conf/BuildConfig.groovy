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
    }
    dependencies {
    }

    plugins {
        compile ":hibernate:$grailsVersion",
                ":spring-security-core:1.2",
                ":spring-security-openid:1.0.3"

        runtime ":resources:1.0", ":jquery:1.6.1.1", ":cloud-foundry:1.0.1", ":markdown:0.2.1"

        build ":tomcat:$grailsVersion"
    }
}
