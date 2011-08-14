//grails.plugin.location.s2 = "${basedir}/../grails-plugins/grails-spring-security-core"
//grails.plugin.location.s2openid = "${basedir}/../grails-plugins/grails-spring-security-openid"

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

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

        mavenRepo "http://localhost:8081/artifactory/plugins-snapshots-local"
    }
    dependencies {
    }

    plugins {
        compile ":hibernate:$grailsVersion",
                ":spring-security-core:1.2",
                ":spring-security-openid:1.0.3"

        runtime ":resources:1.0", ":jquery:1.6.1.1", ":cloud-foundry:1.0.1"

        build ":tomcat:$grailsVersion"
    }
}
