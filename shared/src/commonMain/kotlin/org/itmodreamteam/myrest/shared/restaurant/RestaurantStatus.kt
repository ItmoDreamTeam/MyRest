package org.itmodreamteam.myrest.shared.restaurant

enum class RestaurantStatus(val representation: String) {
    PENDING("Ожидает проверки"),
    ACTIVE("Активен"),
    BLOCKED("Заблокирован"),
    HIDDEN("Скрыт"),
}
