package org.itmodreamteam.myrest.shared.error

data class ClientException(
    val errors: List<ServerError> = emptyList(),
) : RuntimeException() {

    fun handleWithDefaultErrorHandler() {
        if (errors.isNotEmpty()) {
            DefaultErrorHandler.INSTANCE?.handle(this)
        }
    }

    companion object {
        fun unknown(developerMessage: String? = null): ClientException {
            return ClientException(listOf(ServerError.unknown(developerMessage)))
        }
    }
}
