package org.itmodreamteam.myrest.shared.error

data class ClientException(
    val errors: List<ServerError> = emptyList(),
    val type: Type = Type.USER,
) : RuntimeException() {

    enum class Type {
        USER,
        UNAUTHENTICATED,
    }

    companion object {
        fun unknown(developerMessage: String? = null): ClientException {
            return ClientException(listOf(ServerError.unknown(developerMessage)))
        }

        fun unauthenticated(): ClientException {
            return ClientException(type = Type.UNAUTHENTICATED)
        }
    }
}
