package org.itmodreamteam.myrest.server.service.attachment

import org.itmodreamteam.myrest.server.config.StorageProperties
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.repository.AttachmentRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

@Service
class AttachmentServiceImpl(
    private val attachmentRepository: AttachmentRepository,
    private val storageProperties: StorageProperties,
) : AttachmentService {

    override fun downloadAttachment(objectId: UUID): AttachmentResource {
        val attachment = (attachmentRepository.findByObjectId(objectId)
            ?: throw UserException("attachment.not-found"))
        val path = getObjectPath(objectId)
        return AttachmentResource(FileSystemResource(path), attachment.type)
    }

    private fun getObjectPath(objectId: UUID): Path {
        val filename = objectId.toString()
        return Path.of(storageProperties.directory, filename.substring(0, 2), filename)
    }
}
