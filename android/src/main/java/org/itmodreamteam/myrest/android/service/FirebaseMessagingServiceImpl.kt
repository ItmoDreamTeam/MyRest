package org.itmodreamteam.myrest.android.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.itmodreamteam.myrest.shared.messaging.NotificationContent

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i(javaClass.name, "New messaging token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val content = NotificationContent.fromMap(message.data)
        Log.i(javaClass.name, "New notification: $content")
    }
}
