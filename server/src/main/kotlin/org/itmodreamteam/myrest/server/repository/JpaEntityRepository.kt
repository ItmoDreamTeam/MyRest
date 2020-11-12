package org.itmodreamteam.myrest.server.repository

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaEntityRepository<T : JpaEntity> : JpaRepository<T, Long>
