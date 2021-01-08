package org.itmodreamteam.myrest.shared.messaging

import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

interface MessagingClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun registerMessagingToken(registration: MessagingTokenRegistration)
}
