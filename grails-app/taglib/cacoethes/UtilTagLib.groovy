package cacoethes

class UtilTagLib {
 
    def countdown = { attrs ->
        if (!attrs.id) throwTagError "Tag [countdown] requires the [id] attribute"
        if (!attrs.date) throwTagError "Tag [countdown] requires the [date] attribute"

        if (new Date().before(attrs.date)) {
            r.require modules: "countdown" 
            out << "<span id=\"${attrs.id}\"></span> to go"
            r.script {
                """(function() {
                    \$("#${attrs.id}").countdown({
                        until: new Date(${attrs.date.time}),
                        format: "dhM",
                        layout: "{dn} {dl} {hn} {hl} {mn} {ml}" });
                })();"""
            }
        }
        else {
            out << "Deadline has passed!"
        }
    }
}
