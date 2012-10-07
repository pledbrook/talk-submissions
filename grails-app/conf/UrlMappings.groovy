class UrlMappings {

    static mappings = {
        "/login/auth" {
            controller = "openId"
            action = "auth"
        }

        "/login/openIdCreateAccount" {
            controller = "openId"
            action = "createAccount"
        }
        
        "/oauth/success"(controller: "springSecurityOAuth", action: "onSuccess")
        "/oauth/failure"(controller: "springSecurityOAuth", action: "onFailure")

        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/admin/emails/accepted/$forYear?"(controller: "profile", action: "acceptedEmails")
        "/admin/emails/submitted/$forYear?"(controller: "profile", action: "submittedEmails")

        "/"(controller: "submission", action: "list")
        "500"(view: "/error")
    }
}
