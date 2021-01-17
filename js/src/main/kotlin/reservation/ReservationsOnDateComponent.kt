package reservation

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.LinearDimension
import kotlinx.css.paddingBottom
import kotlinx.datetime.LocalDate
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import styled.css
import styled.styledDiv

class ReservationsOnDateComponent(props: Props) :
    RComponent<ReservationsOnDateComponent.Props, ReservationsOnDateComponent.State>(props) {

    init {
        state = State(emptyList())
        getReservations()
    }

    override fun RBuilder.render() {
        val items = state.items
        if (items.isEmpty() && props.optional) {
            return
        }

        h2 {
            +props.caption
        }
        div {
            if (items.isEmpty()) {
                div {
                    +"Бронирований не найдено"
                }
            }
            for (item in items) {
                styledDiv {
                    child(ReservationListItem::class) {
                        attrs {
                            this.item = item
                        }
                    }
                    css {
                        paddingBottom = LinearDimension("10px")
                    }
                }
            }
        }
    }

    private fun getReservations() = GlobalScope.launch {
        try {
            val reservations = props.reservationClient.getReservationsOfUser(props.date)
            setState(State(reservations))
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var date: LocalDate
        var optional: Boolean
        var caption: String
        var errorHandler: ErrorHandler<Any>
        var reservationClient: ReservationClient
    }

    data class State(
        val items: List<ReservationInfo>,
    ) : RState
}
