package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.reservation.ReservationReportService
import org.springframework.core.io.Resource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/restaurants/{restaurantId}/reservations")
class ReservationController(
    private val reservationReportService: ReservationReportService,
) {

    @GetMapping("/reports")
    @PreAuthorize("hasPermission(#restaurantId, 'Restaurant', 'read')")
    fun generateReportForDate(
        @PathVariable restaurantId: Long,
        @RequestParam @DateTimeFormat(iso = ISO.DATE) date: LocalDate,
    ): ResponseEntity<Resource> {
        val content = reservationReportService.generateReportForDate(restaurantId, date)
        return ResponseEntity.ok()
            .header("Content-Disposition", "inline")
            .contentType(MediaType.APPLICATION_PDF)
            .body(content)
    }
}
