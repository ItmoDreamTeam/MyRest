package org.itmodreamteam.myrest.server.service.attachment

import java.util.*

interface AttachmentService {

    fun downloadAttachment(objectId: UUID): AttachmentResource
}
