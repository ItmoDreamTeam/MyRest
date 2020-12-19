package org.itmodreamteam.myrest.shared

import io.ktor.client.request.*

interface AccessTokenProvider {

    val accessToken: String?

    companion object {
        var INSTANCE: AccessTokenProvider? = null

        fun HttpRequestBuilder.provideAccessToken() {
            val token = INSTANCE?.accessToken
            header("Authorization", "Bearer $token")
        }
    }
}
