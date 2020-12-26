package org.itmodreamteam.myrest.server.service.reservation

import org.springframework.core.io.Resource
import java.time.LocalDate

interface ReservationReportService {

    fun generateReportForDate(restaurantId: Long, date: LocalDate): Resource
}
