package org.itmodreamteam.myrest.server.repository.user

import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository

interface IdentifierRepository : JpaEntityRepository<Identifier> {

    fun findByValue(value: String): Identifier?
}
