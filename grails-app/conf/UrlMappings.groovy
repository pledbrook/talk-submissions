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

        "/"(controller: "submission", action: "list")
        "500"(view: "/error")
    }
}
