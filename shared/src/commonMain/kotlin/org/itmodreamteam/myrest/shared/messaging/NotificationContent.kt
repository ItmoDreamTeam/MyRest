package org.itmodreamteam.myrest.shared.messaging

data class NotificationContent(
    val title: String,
    val text: String,
) {
    fun toMap(): Map<String, String> = mapOf(
        "title" to title,
        "text" to text,
    )

    companion object {
        fun fromMap(data: Map<String, String>): NotificationContent {
            return NotificationContent(data["title"]!!, data["text"]!!)
        }
    }
}
