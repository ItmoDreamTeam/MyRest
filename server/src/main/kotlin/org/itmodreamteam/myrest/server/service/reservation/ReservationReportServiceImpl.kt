package org.itmodreamteam.myrest.server.service.reservation

import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerHelper
import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.template.TemplateProcessor
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*
import javax.swing.text.TableView

@Service
class ReservationReportServiceImpl(
    private val restaurantRepository: RestaurantRepository,
    private val reservationRepository: ReservationRepository,
    private val templateProcessor: TemplateProcessor,
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
        return emptyList()
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
        val manager: EmployeeInfo,
        val status: ReservationStatus,
        val activeFrom: LocalDateTime,
        val activeUntil: LocalDateTime,
    )
}
