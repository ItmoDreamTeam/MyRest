package org.itmodreamteam.myrest.server.view.assembler

import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.server.model.messaging.Notification
import org.itmodreamteam.myrest.shared.messaging.NotificationContent
import org.itmodreamteam.myrest.shared.messaging.NotificationView
import org.springframework.stereotype.Component

@Component
class NotificationToNotificationViewAssembler : ModelViewAssembler<Notification, NotificationView> {

    override fun toView(model: Notification): NotificationView {
        return NotificationView(
            model.id,
            model.created.toKotlinLocalDateTime(),
            NotificationContent(model.title, model.text),
            model.seen,
        )
    }
}
