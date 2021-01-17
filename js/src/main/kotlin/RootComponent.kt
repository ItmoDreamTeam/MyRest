import kotlinx.browser.window
import kotlinx.css.LinearDimension
import kotlinx.css.margin
import kotlinx.css.width
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.user.UserClient
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import restaurant.RestaurantSearch
import security.AccessTokenHolder
import styled.css
import styled.styledDiv

interface StateManager {
    fun changeState(state: RootComponent.State)
}

class RootComponent(props: Props) : RComponent<RootComponent.Props, RootComponent.State>(props),
    StateManager {

    init {
        state = State.fromCurrentFragment()
    }

    override fun RBuilder.render() {
        window.location.hash = state.fragment
        styledDiv {
            when (state) {
                State.SEARCH -> child(RestaurantSearch::class) {
                    attrs {
                        errorHandler = props.errorHandler
                        restaurantClient = props.restaurantClient
                    }
                }
            }
            css {
                width = LinearDimension("1000px")
                margin = "auto"
            }
        }
    }

    override fun changeState(state: State) {
        this.state = state
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var accessTokenHolder: AccessTokenHolder
        var userClient: UserClient
        var restaurantClient: RestaurantClient
        var reservationClient: ReservationClient
    }

    enum class State : RState {
        SEARCH,
        PROFILE,
        ;

        val fragment = name

        companion object {
            fun fromCurrentFragment(): State {
                val fragment = window.location.hash
                return values().firstOrNull { it.fragment == fragment } ?: SEARCH
            }
        }
    }
}
