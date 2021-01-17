package user

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.user.Profile
import org.itmodreamteam.myrest.shared.user.UserClient
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.h3
import styled.styledDiv

class ProfileComponent(props: Props) : RComponent<ProfileComponent.Props, ProfileComponent.State>(props) {

    init {
        getMe()
    }

    override fun RBuilder.render() {
        val me = state.me
        styledDiv {
            h2 {
                +"Профиль"
            }
        }
        if (me != null) {
            styledDiv {
                h3 {
                    +"Имя"
                }
                div {
                    +me.fullName()
                }
            }
        }
    }

    private fun getMe() = GlobalScope.launch {
        try {
            val me = props.userClient.getMe()
            setState(State(me))
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var userClient: UserClient
    }

    data class State(
        val me: Profile?,
    ) : RState
}
