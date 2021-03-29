package org.itmodreamteam.myrest.shared.restaurant

enum class ReservationStatus(val representation: String) {
    PENDING("Заявка обрабатывается"),
    APPROVED("Одобрено"),
    REJECTED("Отклонено"),
    IN_PROGRESS("Выполняется"),
    COMPLETED("Завершено"),
}
