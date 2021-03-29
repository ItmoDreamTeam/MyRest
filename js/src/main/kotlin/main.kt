import error.JsErrorHandler
import kotlinx.browser.document
import kotlinx.browser.window
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.restaurant.ReservationClientImpl
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import org.itmodreamteam.myrest.shared.user.UserClientImpl
import react.dom.render
import security.LocalStorageAccessTokenProvider

fun main() {
    window.onload = {
        val accessTokenProvider = LocalStorageAccessTokenProvider()
        AccessTokenProvider.INSTANCE = accessTokenProvider

        render(document.getElementById("root")) {
            child(RootComponent::class) {
                attrs {
                    errorHandler = JsErrorHandler()
                    accessTokenHolder = accessTokenProvider
                    userClient = UserClientImpl()
                    restaurantClient = RestaurantClientImpl()
                    reservationClient = ReservationClientImpl()
                }
            }
        }
    }
}
