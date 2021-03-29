package user

import PreviousPageRedirection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.user.SignInVerification
import org.itmodreamteam.myrest.shared.user.UserClient
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.label
import security.AccessTokenHolder
import styled.css
import styled.styledDiv
import styled.styledInput

class VerificationCodeComponent(props: Props) :
    RComponent<VerificationCodeComponent.Props, VerificationCodeComponent.State>(props) {

    override fun RBuilder.render() {
        label {
            +"Код подтверждения:"
        }
        styledInput {
            attrs {
                onInputFunction = {
                    val code = (it.target as HTMLInputElement).value
                    setState(State(code))
                }
            }
            css {
                width = LinearDimension.fillAvailable
                marginBottom = LinearDimension("5px")
            }
        }
        styledDiv {
            button(type = ButtonType.submit) {
                +"Войти"
                attrs {
                    disabled = !state.codeMatchesPattern
                    onClickFunction = {
                        startSession()
                    }
                }
            }
            css {
                textAlign = TextAlign.center
            }
        }
    }

    private fun startSession() = GlobalScope.launch {
        try {
            val code = state.code ?: return@launch
            val session = props.userClient.startSession(SignInVerification(props.phone, code))
            props.accessTokenHolder.value = session.token
            PreviousPageRedirection.redirectToPreviousUri()
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var phone: String
        var errorHandler: ErrorHandler<Any>
        var accessTokenHolder: AccessTokenHolder
        var userClient: UserClient
    }

    data class State(
        val code: String?,
    ) : RState {

        val codeMatchesPattern: Boolean =
            if (code == null) {
                false
            } else {
                CODE_PATTERN.matches(code)
            }

        companion object {
            private val CODE_PATTERN = Regex("\\d{6}")
        }
    }
}
