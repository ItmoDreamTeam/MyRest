package org.itmodreamteam.myrest.server.repository.messaging

import org.itmodreamteam.myrest.server.model.messaging.MessagingToken
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository

interface MessagingTokenRepository : JpaEntityRepository<MessagingToken> {

    fun findByValue(value: String): MessagingToken?

    fun findByUser(user: User): List<MessagingToken>
}
