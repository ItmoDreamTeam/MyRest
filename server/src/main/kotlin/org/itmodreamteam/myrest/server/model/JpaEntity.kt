package org.itmodreamteam.myrest.server.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class JpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @CreationTimestamp
    lateinit var created: LocalDateTime
        private set

    @UpdateTimestamp
    lateinit var updated: LocalDateTime
        private set
}
