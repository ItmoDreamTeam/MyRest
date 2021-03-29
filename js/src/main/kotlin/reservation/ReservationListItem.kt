package reservation

import kotlinx.css.*
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import styled.css
import styled.styledB
import styled.styledDiv
import styled.styledImg
import util.DateTimeUtil.formatHourMinute

class ReservationListItem(props: Props) : RComponent<ReservationListItem.Props, RState>(props) {

    override fun RBuilder.render() {
        val item = props.item
        val restaurant = item.table.restaurant
        val tableInfo = item.table.info

        div {
            styledImg(src = restaurant.avatar?.url()) {
                css {
                    borderRadius = LinearDimension("10px")
                    width = LinearDimension("150px")
                    height = LinearDimension("150px")
                    marginRight = LinearDimension("10px")
                }
            }

            styledDiv {
                h2 {
                    +restaurant.name
                }
                div {
                    +"Столик № ${tableInfo.number} (Мест: ${tableInfo.numberOfSeats})"
                }
                div {
                    +(formatHourMinute(item.activeFrom) + "—" + formatHourMinute(item.activeUntil))
                }
                div {
                    +"Статус: "
                    styledB {
                        +item.status.representation
                        css {
                            color = when (item.status) {
                                ReservationStatus.PENDING -> Color.blue
                                ReservationStatus.APPROVED -> Color.green
                                ReservationStatus.REJECTED -> Color.red
                                else -> Color.black
                            }
                        }
                    }
                }
                css {
                    display = Display.inlineBlock
                    verticalAlign = VerticalAlign.top
                }
            }
        }
    }

    interface Props : RProps {
        var item: ReservationInfo
    }
}
