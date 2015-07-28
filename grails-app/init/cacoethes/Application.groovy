package cacoethes

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

import javax.sql.DataSource

class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

//    @Bean//(destroyMethod = "close")
//    @Profile("production")
//    DataSource dataSource() throws URISyntaxException {
//        println ">>> Configuring production data source"
//        final uri = new URI(System.getenv("DATABASE_URL") ?:
//                "postgres://pledbrook:password@localhost:5432/ggx_talks")
//
//        final ds = new org.apache.tomcat.jdbc.pool.DataSource();
//        ds.url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path
//        ds.username = uri.userInfo.split(":")[0]
//        ds.password = uri.userInfo.split(":")[1]
//        ds.driverClassName = "org.postgresql.Driver"
//        return ds
//    }
}
