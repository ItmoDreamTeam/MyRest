package org.itmodreamteam.myrest.server.error

data class InterpolatableError(
    val key: String,
    val parameters: Map<String, Any> = emptyMap(),
) {
    fun interpolate(template: String): String {
        var result = template
        for (parameter in parameters) {
            result = result.replace("{" + parameter.key + "}", parameter.value.toString())
        }
        return result
    }
}
