package org.itmodreamteam.myrest.server.service.attachment

import org.springframework.core.io.Resource
import org.springframework.http.MediaType

data class AttachmentResource(
    val resource: Resource,
    val type: String,
) {
    val mediaType: MediaType = try {
        MediaType.valueOf(type)
    } catch (e: Exception) {
        MediaType.APPLICATION_OCTET_STREAM
    }
}
