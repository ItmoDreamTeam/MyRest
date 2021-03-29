package restaurant

import kotlinx.css.*
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv

class RestaurantSearch(props: Props) : RComponent<RestaurantSearch.Props, RestaurantSearch.State>(props),
    RestaurantSearchBar.SearchListener {

    init {
        state = State(ContentPage.empty(Pageable(0, 0)))
    }

    override fun RBuilder.render() {
        styledDiv {
            child(RestaurantSearchBar::class) {
                attrs {
                    errorHandler = props.errorHandler
                    restaurantClient = props.restaurantClient
                    searchListener = this@RestaurantSearch
                }
            }
            css {
                marginTop = LinearDimension("10px")
                marginBottom = LinearDimension("20px")
            }
        }
        div {
            val content = state.items.content
            if (content.isEmpty()) {
                styledDiv {
                    +"Результатов, удовлетворяющих критериям поиска, не найдено"
                    css {
                        textAlign = TextAlign.center
                    }
                }
            }
            for (item in content) {
                styledDiv {
                    child(RestaurantSearchItem::class) {
                        attrs {
                            this.item = item
                        }
                    }
                    css {
                        paddingTop = LinearDimension("20px")
                        borderTopStyle = BorderStyle.solid
                        borderTopColor = Color.blueViolet
                    }
                }
            }
        }
    }

    override fun onSearchCompleted(page: ContentPage<RestaurantInfo>) {
        setState(State(page))
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var restaurantClient: RestaurantClient
    }

    data class State(
        val items: ContentPage<RestaurantInfo>
    ) : RState
}
