import kotlinx.browser.document
import kotlinx.browser.window
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import react.dom.render
import restaurant.RestaurantSearch

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(RestaurantSearch::class) {
                attrs {
                    restaurantClient = RestaurantClientImpl()
                }
            }
        }
    }
}
