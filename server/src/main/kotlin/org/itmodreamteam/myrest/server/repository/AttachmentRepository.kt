package org.itmodreamteam.myrest.server.repository

import org.itmodreamteam.myrest.server.model.Attachment
import java.util.*

interface AttachmentRepository : JpaEntityRepository<Attachment> {

    fun findByObjectId(objectId: UUID): Attachment?
}
