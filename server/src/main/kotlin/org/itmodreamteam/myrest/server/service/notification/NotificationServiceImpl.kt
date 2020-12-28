package org.itmodreamteam.myrest.server.service.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MessagingErrorCode
import org.itmodreamteam.myrest.server.model.messaging.Notification
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.messaging.MessagingTokenRepository
import org.itmodreamteam.myrest.server.repository.messaging.NotificationRepository
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.messaging.NotificationContent
import org.itmodreamteam.myrest.shared.messaging.NotificationView
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val messagingTokenRepository: MessagingTokenRepository,
    private val notificationViewAssembler: ModelViewAssembler<Notification, NotificationView>,
) : NotificationService {

    private val log = LoggerFactory.getLogger(javaClass)

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    override fun notify(user: User, content: NotificationContent) {
        log.info("Notifying user ${user.id}: $content")
        val notification = notificationRepository.save(Notification(content.title, content.text, user))
        send(notification)
    }

    private fun send(notification: Notification) {
        val user = notification.user
        val messageContent = buildMessageContent(notification)
        messagingTokenRepository.findByUser(user).forEach { token ->
            try {
                val message = messageContent
                    .setToken(token.value)
                    .build()
                firebaseMessaging.send(message)
            } catch (e: FirebaseMessagingException) {
                if (e.messagingErrorCode == MessagingErrorCode.UNREGISTERED) {
                    log.info("Unregistering messaging token $token")
                    messagingTokenRepository.delete(token)
                } else {
                    log.warn("Error while notifying user ${user.id}: ${e.messagingErrorCode} ${e.message}")
                }
            }
        }
    }

    private fun buildMessageContent(notification: Notification): Message.Builder {
        val content = notificationViewAssembler.toView(notification).content
        return Message.builder()
            .putAllData(content.toMap())
            .setNotification(
                com.google.firebase.messaging.Notification.builder()
                    .setTitle(content.title)
                    .setBody(content.text)
                    .build()
            )
    }
}
