package restaurant

import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class RestaurantSearchItem(props: Props) : RComponent<RestaurantSearchItem.Props, RState>(props) {

    override fun RBuilder.render() {
        div {
            +props.item.name
        }
    }

    interface Props : RProps {
        var item: RestaurantInfo
    }
}
