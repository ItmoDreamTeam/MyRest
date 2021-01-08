package org.itmodreamteam.myrest.shared.messaging

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.HttpClientProvider

class MessagingClientImpl : MessagingClient {

    private val client = HttpClientProvider.provide()

    override suspend fun registerMessagingToken(registration: MessagingTokenRegistration) {
        return client.put {
            url("/messaging/token")
            provideAccessToken()
            body = registration
        }
    }
}
