package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.shared.ContentPage
import org.springframework.data.domain.Page

object PageUtil {
    fun <E> toContentPage(page: Page<E>): ContentPage<E> {
        return ContentPage(page.content, page.number, page.size, page.totalPages, page.totalElements.toInt())
    }
}
