import cacoethes.auth.*

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        def roleAdmin = new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        def roleUser = new Role(authority: 'ROLE_USER').save(failOnError: true)

        def user = new User(username: 'pledbrook', password: "password", enabled: true).save(failOnError: true)
        def admin = new User(username: 'admin', password: "password", enabled: true).save(failOnError: true)

        UserRole.create user, roleUser
        UserRole.create admin, roleUser
        UserRole.create admin, roleAdmin, true
    }

    def destroy = {
    }
}
