package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.messaging.NotificationUpdate
import org.itmodreamteam.myrest.shared.messaging.NotificationView
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService,
    private val currentUserService: CurrentUserService,
) {

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Notification', 'write')")
    fun update(@PathVariable id: Long, @RequestBody update: NotificationUpdate): NotificationView {
        return notificationService.update(id, update)
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getUserNotifications(pageable: Pageable): ContentPage<NotificationView> {
        val user = currentUserService.currentUserEntity
        return PageUtil.toContentPage(notificationService.getUserNotifications(user, pageable))
    }
}
