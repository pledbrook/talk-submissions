import cacoethes.Audience
import cacoethes.Profile
import cacoethes.TalkCategory
import cacoethes.auth.Role
import cacoethes.auth.User
import cacoethes.auth.UserRole
import grails.util.Environment

class BootStrap {

    def userService

    def init = { servletContext ->
        createReferenceData()
        def roleAdmin = Role.findOrSaveWhere(authority: "ROLE_ADMIN")
        def roleUser = Role.findOrSaveWhere(authority: "ROLE_USER")
        def roleReviewer = Role.findOrSaveWhere(authority: "ROLE_REVIEWER")

        if (Environment.current == Environment.DEVELOPMENT && !User.findByUsername("pledbrook")) {
            def users = []
            users << userService.createUser("pledbrook", "password", enabled: true, profile:
                    new Profile(
                        name: "Peter Ledbrook",
                        email: "p.ledbrook@cacoethes.co.uk",
                        bio: "Some time Grails expert",
                        travelFrom: "London, UK")).save(failOnError: true)
            users << userService.createUser("dilbert", "password", enabled: true, profile:
                    new Profile(
                        name: "Dilbert the cartoon character",
                        email: "dilbert@nowhere.net",
                        bio: "Dysfunctional IT employee",
                        travelFrom: "Springfield, USA")).save(failOnError: true)

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
                        bio: "The super user",
                        travelFrom: "N/A")).save(failOnError: true)

            UserRole.create admin, roleUser
            UserRole.create admin, roleAdmin
            UserRole.create admin, roleReviewer, true
        }
    }

    private void createReferenceData() {
        if (Role.count() > 0) return

        for (r in ["ROLE_ADMIN", "ROLE_USER", "ROLE_REVIEWER"]) {
            new Role(authority: r).save()
        }

        new Audience(experience: "Beginner", orderIndex: 0).save()
        new Audience(experience: "Intermediate", orderIndex: 1).save()
        new Audience(experience: "Expert", orderIndex: 2).save()

        new TalkCategory(name: "Presentation", durationInMinutes: 45).save()
        new TalkCategory(name: "Workshop", durationInMinutes: 180).save(flush: true)
    }
}
