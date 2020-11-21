package org.itmodreamteam.myrest.server.repository.user

import org.itmodreamteam.myrest.server.model.user.Session
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository

interface SessionRepository : JpaEntityRepository<Session> {

    fun findByToken(token: String): Session?
}
