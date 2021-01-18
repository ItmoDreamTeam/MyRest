package reservation

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import util.DateTimeUtil

class ReservationList(props: Props) : RComponent<ReservationList.Props, RState>(props) {

    override fun RBuilder.render() {
        h1 {
            +"Бронирования"
        }

        var date = DateTimeUtil.today()
        val untilDate = date.plus(30, DateTimeUnit.DAY)
        while (date < untilDate) {
            child(ReservationsOnDateComponent::class) {
                attrs {
                    if (date == DateTimeUtil.today()) {
                        caption = "Сегодня"
                        optional = false
                    } else {
                        caption = DateTimeUtil.formatDate(date)
                        optional = true
                    }
                    this.date = date
                    errorHandler = props.errorHandler
                    reservationClient = props.reservationClient
                }
            }
            date = date.plus(1, DateTimeUnit.DAY)
        }
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var reservationClient: ReservationClient
    }
}
