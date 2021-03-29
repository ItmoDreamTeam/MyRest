package restaurant

import kotlinx.css.*
import kotlinx.html.title
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*
import styled.css
import styled.styledDiv
import styled.styledImg

class RestaurantSearchItem(props: Props) : RComponent<RestaurantSearchItem.Props, RState>(props) {

    override fun RBuilder.render() {
        val item = props.item
        div {
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

                div {
                    span {
                        +"⭐ "
                    }
                    span {
                        attrs {
                            title = "Внутренний рейтинг"
                        }
                        +item.internalRating.toString()
                    }
                    span {
                        +" / "
                    }
                    span {
                        attrs {
                            title = "Внешний рейтинг"
                        }
                        +item.externalRating.toString()
                    }
                }

                if (item.websiteUrl != null) {
                    div {
                        a(href = item.websiteUrl, target = "_blank") {
                            +item.websiteUrl!!
                        }
                    }
                }
                if (item.email != null) {
                    div {
                        a(href = "mailto:${item.email}") {
                            +item.email!!
                        }
                    }
                }
                if (item.phone != null) {
                    div {
                        +item.phone!!
                    }
                }

                css {
                    display = Display.inlineBlock
                    verticalAlign = VerticalAlign.top
                }
            }
            div {
                p {
                    +item.description
                }
            }
        }
    }

    interface Props : RProps {
        var item: RestaurantInfo
    }
}
