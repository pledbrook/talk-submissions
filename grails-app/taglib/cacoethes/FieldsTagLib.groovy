package cacoethes

import grails.core.GrailsDomainClass
import org.grails.validation.DomainClassPropertyComparator

/**
 * Created by pledbrook on 02/08/15.
 */
class FieldsTagLib {
    static namespace = "app"

    def markdownService

    def mdToHtml = { attrs, body ->
        out << markdownService.mdToHtml((attrs.text ?: body())?.toString())
    }

    /**
     * @attr bean REQUIRED the bean
     */
    def displayFields = { attrs ->
        if (attrs.bean == null) throwTagError "Tag [displayFields] requires the [bean] attribute"

        def descriptor = resolveDomainClass(attrs.bean)
        def tmplModel = [
                bean: attrs.bean,
                domainClassDesc: descriptor,
                comparator: new DomainClassPropertyComparator(descriptor),
                excludes: attrs.excludes ?: [],
                markdownProps: attrs.markdownProps ?: []]
        out << g.render(template: "/shared/showFields", model: tmplModel)
    }

    private GrailsDomainClass resolveDomainClass(bean) {
        resolveDomainClass(bean.getClass())
    }

    private GrailsDomainClass resolveDomainClass(Class beanClass) {
        grailsApplication.getDomainClass(beanClass.name)
    }
}
