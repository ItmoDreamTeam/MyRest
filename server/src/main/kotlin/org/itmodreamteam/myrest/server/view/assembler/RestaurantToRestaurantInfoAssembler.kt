package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.Attachment
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.shared.attachment.AttachmentMetadata
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.springframework.stereotype.Component

@Component
class RestaurantToRestaurantInfoAssembler(
    private val attachmentMetadataAssembler: ModelViewAssembler<Attachment, AttachmentMetadata>,
) : ModelViewAssembler<Restaurant, RestaurantInfo> {

    override fun toView(model: Restaurant): RestaurantInfo {
        return RestaurantInfo(
            model.id,
            model.status,
            model.name,
            model.description,
            model.legalInfo,
            model.websiteUrl,
            model.phone,
            model.email,
            model.internalRating,
            model.externalRating,
            if (model.avatar != null) attachmentMetadataAssembler.toView(model.avatar!!) else null,
            attachmentMetadataAssembler.toViewList(model.photos),
        )
    }
}
