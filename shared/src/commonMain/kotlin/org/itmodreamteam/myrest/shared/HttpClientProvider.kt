package org.itmodreamteam.myrest.shared

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*

object HttpClientProvider {

    fun provide(): HttpClient = HttpClient {
        defaultRequest {
            host = ClientProperties.Server.host
            port = ClientProperties.Server.port
        }
        install(JsonFeature)
    }
}
