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
                marginBottom = LinearDimension("10px")
            }
        }
        styledDiv {
            for (item in state.items.content) {
                styledDiv {
                    child(RestaurantSearchItem::class) {
                        attrs {
                            this.item = item
                        }
                    }
                    css {
                        height = LinearDimension("150px")
                        width = LinearDimension.fillAvailable
                        marginBottom = LinearDimension("10px")
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
