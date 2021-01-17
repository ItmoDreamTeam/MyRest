import error.JsErrorHandler
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.LinearDimension
import kotlinx.css.margin
import kotlinx.css.width
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import react.dom.render
import restaurant.RestaurantSearch
import security.LocalStorageAccessTokenProvider
import styled.css
import styled.styledDiv

fun main() {
    window.onload = {
        AccessTokenProvider.INSTANCE = LocalStorageAccessTokenProvider()

        render(document.getElementById("root")) {
            styledDiv {
                child(RestaurantSearch::class) {
                    attrs {
                        errorHandler = JsErrorHandler()
                        restaurantClient = RestaurantClientImpl()
                    }
                }
                css {
                    width = LinearDimension("1000px")
                    margin = "auto"
                }
            }
        }
    }
}
