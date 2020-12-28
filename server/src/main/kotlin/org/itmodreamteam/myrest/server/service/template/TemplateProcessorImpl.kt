package org.itmodreamteam.myrest.server.service.template

import freemarker.core.HTMLOutputFormat
import freemarker.template.Configuration
import org.springframework.stereotype.Component
import java.io.StringWriter

@Component
class TemplateProcessorImpl(private val configuration: Configuration) : TemplateProcessor {

    override fun applyModel(name: String, model: Any?): String {
        configuration.outputFormat = HTMLOutputFormat.INSTANCE
        return StringWriter().use {
            configuration.getTemplate("$name.ftl").process(model, it)
            it.toString()
        }
    }
}
