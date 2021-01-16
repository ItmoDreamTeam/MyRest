import error.JsErrorHandler
import kotlinx.browser.document
import kotlinx.browser.window
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import react.dom.render
import restaurant.RestaurantSearch
import security.LocalStorageAccessTokenProvider

fun main() {
    window.onload = {
        AccessTokenProvider.INSTANCE = LocalStorageAccessTokenProvider()

        render(document.getElementById("root")) {
            child(RestaurantSearch::class) {
                attrs {
                    errorHandler = JsErrorHandler()
                    restaurantClient = RestaurantClientImpl()
                }
            }
        }
    }
}
