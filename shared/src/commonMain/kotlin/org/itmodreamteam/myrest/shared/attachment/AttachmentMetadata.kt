package org.itmodreamteam.myrest.shared.attachment

import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.ClientProperties.Server

@Serializable
data class AttachmentMetadata(
    val objectId: String,
    val name: String,
    val type: String,
) {
    fun url(): String = "http://${Server.host}:${Server.port}/attachments/$objectId/$name"
}
