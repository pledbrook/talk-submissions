import cacoethes.auth.*
import grails.util.Environment

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        def roleAdmin = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def roleUser = Role.findOrSaveWhere(authority: "ROLE_USER")

        if (Environment.current == Environment.DEVELOPMENT && !User.findByUsername("pledbrook")) {
            def users = []
            users << new User(username: "pledbrook", password: "password", enabled: true).save(failOnError: true)
            users << new User(username: "dilbert", password: "password", enabled: true).save(failOnError: true)

            for (u in users) {
                UserRole.create u, roleUser
            }
        }

        if (!User.findByUsername("admin")) {
            def adminPassword = System.getProperty("admin.password") ?: "changeit"
            def admin = new User(username: "admin", password: adminPassword, enabled: true).save(failOnError: true)

            UserRole.create admin, roleUser
            UserRole.create admin, roleAdmin, true
        }
    }

    def destroy = {
    }
}
