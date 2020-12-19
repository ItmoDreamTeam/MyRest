package org.itmodreamteam.myrest.shared.error

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val key: String,
    val userMessage: String = UNKNOWN_ERROR_USER_MESSAGE,
    val developerMessage: String? = null,
) {
    companion object {
        const val UNKNOWN_ERROR_KEY = "unknown"
        const val UNKNOWN_ERROR_USER_MESSAGE = "Ошибка сервера. Повторите попытку позже"

        fun unknown(developerMessage: String? = null) =
            Error(UNKNOWN_ERROR_KEY, UNKNOWN_ERROR_USER_MESSAGE, developerMessage)
    }
}
