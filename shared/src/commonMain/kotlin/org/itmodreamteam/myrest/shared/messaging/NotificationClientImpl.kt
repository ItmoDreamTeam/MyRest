package org.itmodreamteam.myrest.shared.messaging

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.HttpClientProvider
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.Pageable.Companion.addPageableParameters

class NotificationClientImpl : NotificationClient {

    private val client = HttpClientProvider.provide()

    override suspend fun update(id: Long, update: NotificationUpdate): NotificationView {
        return client.put {
            url("/notifications/$id")
            provideAccessToken()
            body = update
        }
    }

    override suspend fun getUserNotifications(pageable: Pageable): ContentPage<NotificationView> {
        return client.get {
            url("/notifications")
            addPageableParameters(pageable)
            provideAccessToken()
        }
    }
}
