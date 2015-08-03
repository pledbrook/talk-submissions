package cacoethes

class UtilTagLib {
 
    def countdown = { attrs ->
        if (!attrs.id) throwTagError "Tag [countdown] requires the [id] attribute"
        if (!attrs.date) throwTagError "Tag [countdown] requires the [date] attribute"

        if (new Date().before(attrs.date)) {
//            out << asset.javascript(src: "http://cdnjs.cloudflare.com/ajax/libs/jquery-countdown/1.6.1/jquery.countdown.min.js")
            out << "<span id=\"${attrs.id}\"></span> to go"
            out << asset.script {
                """(function() {
                    \$("#${attrs.id}").countdown({
                        until: new Date(${attrs.date.time}),
                        format: "dhM",
                        layout: "{dn} {dl} {hn} {hl} {mn} {ml}" });
                })();""".stripIndent()
            }
        }
        else {
            out << "Deadline has passed!"
        }
    }
}
