package org.itmodreamteam.myrest.server.service.reservation

import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerHelper
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.server.service.table.TableService
import org.itmodreamteam.myrest.server.service.template.TemplateProcessor
import org.itmodreamteam.myrest.server.service.user.UserService
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.itmodreamteam.myrest.shared.table.TableView
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

@Service
class ReservationReportServiceImpl(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantTableRepository: RestaurantTableRepository,
    private val reservationRepository: ReservationRepository,
    private val templateProcessor: TemplateProcessor,
    private val userService: UserService,
    private val tableService: TableService,
    private val employeeService: EmployeeService,
) : ReservationReportService {

    override fun generateReportForDate(restaurantId: Long, date: LocalDate): Resource {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw UserException("Ресторан с ID=$restaurantId не найден")
        val html = generateHtmlReportForDate(restaurant, date)
        return InputStreamResource(htmlToPdf(html))
    }

    private fun generateHtmlReportForDate(restaurant: Restaurant, date: LocalDate): String {
        val reservationEntries = getReservationEntries(restaurant, date)
        val formattedDate = date.format(ofPattern("d MMMM yyyy г.", Locale("ru")))
        val model = mapOf(Pair("reservations", reservationEntries), Pair("date", formattedDate))
        return templateProcessor.applyModel("report/reservations", model)
    }

    private fun getReservationEntries(restaurant: Restaurant, date: LocalDate): List<ReservationEntry> {
        val statuses = ReservationStatus.values().toList()
        val activeFrom = date.atStartOfDay()
        val activeUntil = date.plusDays(1).atStartOfDay()
        return restaurantTableRepository.findByRestaurant(restaurant).flatMap { table ->
            reservationRepository.findReservationsForTableByStatusesAndTimeRangeOverlapping(
                table, statuses, activeFrom, activeUntil
            ).map { toReservationEntry(it) }
        }
    }

    private fun toReservationEntry(reservation: Reservation): ReservationEntry {
        return ReservationEntry(
            reservation.id,
            userService.toProfile(reservation.user),
            tableService.toTableView(reservation.table),
            if (reservation.manager != null) employeeService.toEmployeeInfo(reservation.manager!!) else null,
            reservation.status,
            reservation.activeFrom.toLocalTime(),
            reservation.activeUntil.toLocalTime(),
        )
    }

    private fun htmlToPdf(html: String): InputStream {
        val outputStream = ByteArrayOutputStream()
        val document = Document(PageSize.A4, 50F, 50F, 60F, 60F)
        val writer = PdfWriter.getInstance(document, outputStream)
        document.open()
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, html.byteInputStream())
        document.close()
        return ByteArrayInputStream(outputStream.toByteArray())
    }

    data class ReservationEntry(
        val id: Long,
        val user: Profile,
        val table: TableView,
        val manager: EmployeeInfo?,
        val status: ReservationStatus,
        val activeFrom: LocalTime,
        val activeUntil: LocalTime,
    ) {
        val statusDescription = when (status) {
            ReservationStatus.PENDING -> "Ожидание"
            ReservationStatus.APPROVED -> "Одобрено"
            ReservationStatus.REJECTED -> "Отклонено"
            ReservationStatus.IN_PROGRESS -> "Выполняется"
            ReservationStatus.COMPLETED -> "Завершено"
        }
    }
}
