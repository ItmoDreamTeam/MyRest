package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.attachment.AttachmentService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AttachmentController(private val attachmentService: AttachmentService) {

    @GetMapping("/attachments/{objectId}/*")
    fun downloadAttachment(@PathVariable objectId: UUID): ResponseEntity<Resource> {
        val attachment = attachmentService.downloadAttachment(objectId)
        return ResponseEntity.ok()
            .header("Content-Disposition", "inline")
            .contentType(attachment.mediaType)
            .body(attachment.resource)
    }
}
