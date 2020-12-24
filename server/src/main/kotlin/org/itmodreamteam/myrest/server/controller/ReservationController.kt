package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.reservation.ReservationReportService
import org.itmodreamteam.myrest.server.service.reservation.ReservationViewService
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
class ReservationController(
    private val reservationReportService: ReservationReportService,
    private val reservationViewService: ReservationViewService,
) {

    @GetMapping("/restaurants/{restaurantId}/reservations/reports")
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

    @GetMapping("/restaurants/{restaurantId}/reservations")
    @PreAuthorize("hasPermission(#restaurantId, 'Restaurant', 'read')")
    fun getReservationsOfRestaurant(
        @PathVariable restaurantId: Long,
        @RequestParam @DateTimeFormat(iso = ISO.DATE) date: LocalDate
    ): List<ReservationInfo> {
        return reservationViewService.getReservationsOfRestaurant(restaurantId, date)
    }

    @GetMapping("/reservations")
    @PreAuthorize("isAuthenticated()")
    fun getReservationsOfUser(
        authentication: UserAuthentication,
        @RequestParam @DateTimeFormat(iso = ISO.DATE) date: LocalDate
    ): List<ReservationInfo> {
        val userId = authentication.principal.id
        return reservationViewService.getReservationsOfUser(userId, date)
    }

    @PutMapping("/reservations")
    @PreAuthorize("isAuthenticated()")
    fun submitReservationForApproval(
        @RequestParam tableId: Long,
        @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) activeFrom: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) activeUntil: LocalDateTime,
    ): ReservationInfo {
        return reservationViewService.submitReservationForApproval(tableId, activeFrom, activeUntil)
    }

    @PutMapping("/reservations/{reservationId}/reject")
    @PreAuthorize("hasPermission(#reservationId, 'Reservation', 'write-manager')")
    fun reject(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.reject(reservationId)
    }

    @PutMapping("/reservations/{reservationId}/approve")
    @PreAuthorize("hasPermission(#reservationId, 'Reservation', 'write-manager')")
    fun approve(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.approve(reservationId)
    }

    @PutMapping("/reservations/{reservationId}/start")
    @PreAuthorize("hasPermission(#reservationId, 'Reservation', 'write-manager')")
    fun start(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.start(reservationId)
    }

    @PutMapping("/reservations/{reservationId}/complete")
    @PreAuthorize("hasPermission(#reservationId, 'Reservation', 'write-manager')")
    fun complete(@PathVariable reservationId: Long): ReservationInfo {
        return reservationViewService.complete(reservationId)
    }
}
