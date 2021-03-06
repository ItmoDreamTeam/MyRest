package restaurant

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.LinearDimension
import kotlinx.css.width
import kotlinx.html.js.onInputFunction
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledInput

class RestaurantSearchBar(props: Props) : RComponent<RestaurantSearchBar.Props, RState>(props) {

    init {
        search("")
    }

    override fun RBuilder.render() {
        styledInput {
            attrs {
                placeholder = "Поиск ресторанов..."
                onInputFunction = {
                    val text = (it.target as HTMLInputElement).value
                    search(text)
                }
            }
            css {
                width = LinearDimension.fillAvailable
            }
        }
    }

    private fun search(text: String) = GlobalScope.launch {
        try {
            val page = props.restaurantClient.searchNonGeneric(text, Pageable(0, 100)).toGeneric()
            props.searchListener.onSearchCompleted(page)
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var restaurantClient: RestaurantClient
        var searchListener: SearchListener
    }

    interface SearchListener {
        fun onSearchCompleted(page: ContentPage<RestaurantInfo>)
    }
}
