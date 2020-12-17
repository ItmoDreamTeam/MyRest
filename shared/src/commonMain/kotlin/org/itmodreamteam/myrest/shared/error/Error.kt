package org.itmodreamteam.myrest.shared.error

data class Error(
    val key: String,
    val userMessage: String,
    val developerMessage: String,
) {
    companion object {
        const val UNKNOWN_ERROR_KEY = "unknown"

        fun unknown(developerMessage: String) =
            Error(UNKNOWN_ERROR_KEY, "Ошибка сервера. Повторите попытку позже", developerMessage)
    }
}
