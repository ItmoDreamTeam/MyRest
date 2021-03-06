package org.itmodreamteam.myrest.shared

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ServerError

object HttpClientProvider {

    fun provide(): HttpClient = HttpClient {
        defaultRequest {
            host = ClientProperties.Server.host
            port = ClientProperties.Server.port
            contentType(ContentType.Application.Json)
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        HttpResponseValidator {
            validateResponse { response ->
                when (response.status) {
                    HttpStatusCode.Unauthorized -> {
                        throw ClientException.unauthenticated()
                    }
                }
                if (!response.status.isSuccess()) {
                    val errors = try {
                        val body = response.content.readUTF8Line()!!
                        Json.decodeFromString<List<ServerError>>(body)
                    } catch (e: Exception) {
                        throw ClientException.unknown(e.message)
                    }
                    throw ClientException(errors)
                }
            }
            handleResponseException {
                if (it !is ClientException) {
                    throw ClientException.unknown(it.message)
                }
            }
        }
    }
}
