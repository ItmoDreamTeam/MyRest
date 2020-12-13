package org.itmodreamteam.myrest.server.service.template

interface TemplateProcessor {

    fun applyModel(name: String, model: Any? = null): String
}
