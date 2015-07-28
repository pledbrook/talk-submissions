import cacoethes.Profile
import cacoethes.auth.Role
import cacoethes.auth.User
import cacoethes.auth.UserRole
import grails.util.Environment

class BootStrap {

    def userService
    def dataSource

    def init = { servletContext ->
        def roleAdmin = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def roleUser = Role.findOrSaveWhere(authority: "ROLE_USER")

        if (Environment.current == Environment.DEVELOPMENT && !User.findByUsername("pledbrook")) {
            def users = []
            users << userService.createUser("pledbrook", "password", enabled: true, profile:
                    new Profile(
                        name: "Peter Ledbrook",
                        email: "p.ledbrook@cacoethes.co.uk",
                        bio: "Some time Grails expert")).save(failOnError: true)
            users << userService.createUser("dilbert", "password", enabled: true, profile:
                    new Profile(
                        name: "Dilbert the cartoon character",
                        email: "dilbert@nowhere.net",
                        bio: "Dysfunctional IT employee")).save(failOnError: true)

            for (u in users) {
                UserRole.create u, roleUser
            }
        }

        if (!User.findByUsername("admin")) {
            def adminPassword = System.getProperty("admin.password") ?: "changeit"
            def admin = userService.createUser("admin", adminPassword, enabled: true, profile:
                    new Profile(
                        name: "Admin User",
                        email: "admin@nowhere.net",
                        bio: "The super user")).save(failOnError: true)

            UserRole.create admin, roleUser
            UserRole.create admin, roleAdmin, true
        }
    }
    def destroy = {
    }
}
