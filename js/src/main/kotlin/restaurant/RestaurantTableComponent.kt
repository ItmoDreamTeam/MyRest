package restaurant

import kotlinx.html.js.onClickFunction
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.table.TableView
import react.*
import react.dom.div
import react.dom.h3
import react.dom.input
import react.dom.p

class RestaurantTableComponent(props: Props) : RComponent<RestaurantTableComponent.Props, RestaurantTableComponent.State>(props) {

    override fun RBuilder.render() {
        val tables = state.tables
        div {
            h3 {
                +"Выберете столик"
            }
            for (table in tables) {
                p {
                    attrs {
                        onClickFunction = {
                            setState {
                                selectedTableId = table.id
                            }
                        }
                    }
                    if (table.id == state.selectedTableId) {
                        +"▶ "
                    }
                    + "Номер столика: ${table.info.number}, количество мест: ${table.info.numberOfSeats}"
                }
            }
            h3 {
                +"Выберете дату"
            }
            input {

            }
        }
    }

    interface Props: RProps {
        var restaurant: RestaurantInfo
    }

    data class State (
        val tables: List<TableView>,
        var selectedTableId: Long
    ) : RState
}