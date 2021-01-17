package restaurant

import kotlinx.css.*
import kotlinx.css.properties.border
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.h2
import react.dom.p
import react.dom.span
import styled.css
import styled.styledDiv
import styled.styledImg

class RestaurantSearchItem(props: Props) : RComponent<RestaurantSearchItem.Props, RState>(props) {

    override fun RBuilder.render() {
        val item = props.item
        styledImg(src = item.avatar?.url()) {
            css {
                borderRadius = LinearDimension("10px")
                width = LinearDimension("150px")
                height = LinearDimension("150px")
                marginRight = LinearDimension("10px")
            }
        }
        styledDiv {
            h2 {
                +item.name
            }
            p {
                +item.description
            }

            if (item.websiteUrl != null) {
                a(href = item.websiteUrl, target = "_blank") {
                    +item.websiteUrl!!
                }
                +" "
            }
            if (item.email != null) {
                a(href = "mailto:${item.email}") {
                    +item.email!!
                }
                +" "
            }
            if (item.phone != null) {
                span {
                    +item.phone!!
                }
                +" "
            }

            css {
                display = Display.inlineBlock
                border(LinearDimension("1px"), BorderStyle.solid, Color.brown)
            }
        }
    }

    interface Props : RProps {
        var item: RestaurantInfo
    }
}
