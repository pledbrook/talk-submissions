package cacoethes

import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import org.pegdown.PegDownProcessor

@Transactional
class MarkdownService {

    @Cacheable("markdown")
    def mdToHtml(String text) {
        def processor = new PegDownProcessor()
        processor.markdownToHtml(text)
    }
}
