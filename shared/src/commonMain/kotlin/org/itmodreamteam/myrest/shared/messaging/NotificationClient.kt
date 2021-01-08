package org.itmodreamteam.myrest.shared.messaging

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

interface NotificationClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun update(id: Long, update: NotificationUpdate): NotificationView

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getUserNotifications(pageable: Pageable): ContentPage<NotificationView>
}
