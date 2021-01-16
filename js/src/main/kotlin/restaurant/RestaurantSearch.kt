package restaurant

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class RestaurantSearch(props: Props) : RComponent<RestaurantSearch.Props, RestaurantSearch.State>(props),
    RestaurantSearchBar.SearchListener {

    init {
        state = State(ContentPage.empty(Pageable(0, 0)))
    }

    override fun RBuilder.render() {
        child(RestaurantSearchBar::class) {
            attrs {
                errorHandler = props.errorHandler
                restaurantClient = props.restaurantClient
                searchListener = this@RestaurantSearch
            }
        }
        for (item in state.items.content) {
            child(RestaurantSearchItem::class) {
                attrs {
                    this.item = item
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
