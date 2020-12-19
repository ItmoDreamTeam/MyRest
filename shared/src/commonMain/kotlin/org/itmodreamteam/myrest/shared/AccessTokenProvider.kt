package org.itmodreamteam.myrest.shared

import io.ktor.client.request.*
import kotlin.native.concurrent.ThreadLocal

interface AccessTokenProvider {

    val accessToken: String?

    @ThreadLocal
    companion object {
        var INSTANCE: AccessTokenProvider? = null

        fun HttpRequestBuilder.provideAccessToken() {
            val token = INSTANCE?.accessToken
            header("Authorization", "Bearer $token")
        }
    }
}
