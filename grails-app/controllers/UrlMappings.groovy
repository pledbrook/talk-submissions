class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/admin/emails/accepted/$forYear?"(controller: "profile", action: "acceptedEmails") {
            constraints {
                forYear matches: /\d{4}/
            }
        }

        "/admin/emails/submitted/$forYear?"(controller: "profile", action: "submittedEmails") {
            constraints {
                forYear matches: /\d{4}/
            }
        }

        "/admin/$controller/$action?/$id?" {
            constraints {
                controller inList: ["timeSlot", "track", "talkAssignment", "mailTemplate", "user"]
            }
        }

        "/"(controller: "submission", action: "list")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
