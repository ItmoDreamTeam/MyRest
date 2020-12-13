package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.reservation.ReservationReportService
import org.itmodreamteam.myrest.server.service.restaurant.ReservationViewService
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.springframework.core.io.Resource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/restaurants/{restaurantId}/reservations")
class ReservationController(
    private val reservationReportService: ReservationReportService,
    private val reservationViewService: ReservationViewService,
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

    @PutMapping
    fun submitReservationForApproval(
        @RequestParam tableId: Long,
        @RequestParam activeFrom: LocalDateTime,
        @RequestParam activeUntil: LocalDateTime,
    ): ReservationInfo {
        return reservationViewService.submitReservationForApproval(tableId, activeFrom, activeUntil)
    }

    @PutMapping("/{reservationId}/reject")
    fun reject(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.reject(reservationId)
    }

    @PutMapping("/{reservationId}/approve")
    fun approve(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.approve(reservationId)
    }

    @PutMapping("/{reservationId}/start")
    fun start(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.start(reservationId)
    }

    @PutMapping("/{reservationId}/complete")
    fun complete(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.complete(reservationId)
    }
}
