package user

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.user.Profile
import org.itmodreamteam.myrest.shared.user.UserClient
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.h3
import styled.css
import styled.styledDiv
import styled.styledImg

class ProfileComponent(props: Props) : RComponent<ProfileComponent.Props, ProfileComponent.State>(props) {

    init {
        getMe()
        getMyRestaurants()
    }

    override fun RBuilder.render() {
        h2 {
            +"Профиль"
        }

        val me = state.me
        if (me != null) {
            h3 {
                +"Имя"
            }
            div {
                +me.fullName()
            }

            if (me.role != null) {
                h3 {
                    +"Роль"
                }
                div {
                    +me.role!!.representation
                }
            }
        }

        val restaurants = state.restaurants
        if (!restaurants.isNullOrEmpty()) {
            h3 {
                +"Рестораны"
            }
            for (item in restaurants) {
                val restaurant = item.restaurant
                styledDiv {
                    styledImg(src = restaurant.avatar?.url()) {
                        css {
                            borderRadius = LinearDimension("10px")
                            width = LinearDimension("150px")
                            height = LinearDimension("150px")
                            marginRight = LinearDimension("10px")
                        }
                    }
                    styledDiv {
                        div {
                            +("Название: " + restaurant.name)
                        }
                        div {
                            +("Статус ресторана: " + restaurant.status.representation)
                        }
                        div {
                            +("Роль: " + item.position.representation)
                        }
                        div {
                            +("Статус сотрудника: " + if (item.active()) "Активен" else "Неактивен")
                        }
                        css {
                            display = Display.inlineBlock
                        }
                    }
                    css {
                        marginBottom = LinearDimension("20px")
                    }
                }
            }
        }
    }

    private fun getMe() = GlobalScope.launch {
        try {
            val me = props.userClient.getMe()
            setState(State(me, state.restaurants))
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    private fun getMyRestaurants() = GlobalScope.launch {
        try {
            val restaurants = props.restaurantClient.getRestaurantsOfUser()
            setState(State(state.me, restaurants))
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var userClient: UserClient
        var restaurantClient: RestaurantClient
    }

    data class State(
        val me: Profile?,
        val restaurants: List<EmployeeInfo>?,
    ) : RState
}
