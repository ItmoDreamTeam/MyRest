package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.Attachment
import org.itmodreamteam.myrest.shared.attachment.AttachmentMetadata
import org.springframework.stereotype.Component

@Component
class AttachmentToAttachmentMetadataAssembler : ModelViewAssembler<Attachment, AttachmentMetadata> {

    override fun toView(model: Attachment): AttachmentMetadata {
        return AttachmentMetadata(
            model.objectId.toString(),
            model.name,
            model.type,
        )
    }
}
