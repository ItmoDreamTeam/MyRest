package org.itmodreamteam.myrest.server.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "attachments", uniqueConstraints = [
        UniqueConstraint(columnNames = ["objectId"]),
    ]
)
class Attachment : JpaEntity() {

    @NotNull
    lateinit var objectId: UUID

    @NotNull
    lateinit var name: String

    @NotNull
    lateinit var type: String
}
