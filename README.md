Talk Submissions Application
============================

This is a simple Grails application for accepting talk submissions for conferences.
It allows people to log in via Twitter or Facebook, create a profile and then submit
abstracts. It also allows the conference organiser to review submissions, assign them
to time slots, and send emails for acceptance and rejection. It also has a simple
schedule view.

There is still much work to be done to make it really usable and re-usable.

Getting Started
---------------

The application requires a database and access to a mail server, but that's all. You
also need to specify some configuration to allow Twitter & Facebook authentication and
the sending of emails. Once that's done, you can test it out via

    ./grailsw run-app

Note that if you don't have Grails installed, this command will take a while to complete
the first time round as it has to download Grails.

At the moment, the configuration should either go in grails-app/conf/Config.groovy or
in a GRAILS_APP_CONFIG environment variable as a JSON string. The relevant config
options that need valid values are

    oauth {
        providers {
            twitter {
                ...
                key = 'rIafXlyPcGRhTJsXRbrZjg'
                secret = 'gDiqDmb3qG8OoFwa5ztNwk0yHvz5T9oUSapZKP1k'
                callback = "${grails.serverURL}/oauth/twitter/callback"
            }
            ...
        }
    }

and

    sendgrid {
        username = '*******'
        password = '********'
    }
    grails.mail.default.from = "admin@nowhere.net"

The latter options are self-explanatory, but the OAuth ones require you to create
developer accounts on the relevant service (Twitter or Facebook) and generate API
keys for this application. Also, the above syntax is for Config.groovy and is not
JSON.

[TODO: Add command to convert ConfigSlurper syntax to a JSON string]

Customisation
-------------

If you want to use this app, you almost certainly want to change the look and feel,
or the header and footer at least. The place to do this is in

    grails-app/views/layouts/main.gsp

Note that this means modifying application source files, so effectively requires
you to fork the project. There still needs to be easier customisation, either by
making this app a plugin, or by specifying a directory in which to put custom
stuff.


