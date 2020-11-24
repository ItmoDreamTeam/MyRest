package org.itmodreamteam.myrest.server.repository.user

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.itmodreamteam.myrest.shared.user.Role

interface UserRepository : JpaEntityRepository<User> {
    fun findByRole(role: Role): List<User>
}