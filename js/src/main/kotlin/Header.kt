import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.user.Profile
import org.itmodreamteam.myrest.shared.user.UserClient
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.StyleSheet
import styled.css
import styled.styledDiv

class Header(props: Props) : RComponent<Header.Props, Header.State>(props) {

    init {
        getMe()
    }

    override fun RBuilder.render() {
        val me = state.me
        styledDiv {
            styledDiv {
                +"Рестораны"
                attrs {
                    onClickFunction = {
                        props.stateManager.changeState(RootComponent.State.SEARCH)
                    }
                }
                css {
                    +HeaderStyles.item
                }
            }
            styledDiv {
                +"Бронирования"
                attrs {
                    onClickFunction = {
                        props.stateManager.changeState(RootComponent.State.RESERVATIONS)
                    }
                }
                css {
                    +HeaderStyles.item
                }
            }
            styledDiv {
                +(me?.fullName() ?: "Войти")
                attrs {
                    onClickFunction = {
                        if (me == null) {
                            props.stateManager.changeState(RootComponent.State.SIGN_IN)
                        } else {
                            props.stateManager.changeState(RootComponent.State.PROFILE)
                        }
                    }
                }
                css {
                    +HeaderStyles.profile
                }
            }
            css {
                width = LinearDimension.fillAvailable
                height = LinearDimension("26px")
                marginBottom = LinearDimension("10px")
                color = Color.white
                backgroundColor = Color.blueViolet
            }
        }
    }

    private fun getMe() = GlobalScope.launch {
        try {
            val me = props.userClient.getMe()
            setState(State(me))
        } catch (e: ClientException) {
            if (e.type == ClientException.Type.UNAUTHENTICATED) {
                setState(State(null))
            } else {
                props.errorHandler.handle(this, e)
            }
        }
    }

    interface Props : RProps {
        var stateManager: StateManager
        var errorHandler: ErrorHandler<Any>
        var userClient: UserClient
    }

    data class State(
        val me: Profile?,
    ) : RState

    object HeaderStyles : StyleSheet("header", isStatic = true) {
        val item by css {
            display = Display.inlineBlock
            height = LinearDimension.fillAvailable
            width = LinearDimension("100px")
            margin = "5px"
            textAlign = TextAlign.center

            hover {
                fontWeight = FontWeight.bold
                cursor = Cursor.pointer
            }
        }

        val profile by css {
            display = Display.inlineBlock
            height = LinearDimension.fillAvailable
            width = LinearDimension.fitContent
            maxWidth = LinearDimension("300px")
            margin = "5px"
            textAlign = TextAlign.right
            float = Float.right

            hover {
                fontWeight = FontWeight.bold
                cursor = Cursor.pointer
            }
        }
    }
}
