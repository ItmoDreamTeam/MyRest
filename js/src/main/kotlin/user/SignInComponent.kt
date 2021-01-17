package user

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.user.SignIn
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

class SignInComponent(props: Props) : RComponent<SignInComponent.Props, SignInComponent.State>(props) {

    init {
        state = State(null)
    }

    override fun RBuilder.render() {
        styledDiv {
            label {
                +"Номер телефона:"
            }
            styledInput {
                attrs {
                    readonly = state.verificationRequested
                    placeholder = "79123456789"
                    onInputFunction = {
                        val phone = (it.target as HTMLInputElement).value
                        setState(State(phone))
                    }
                }
                css {
                    width = LinearDimension.fillAvailable
                    marginBottom = LinearDimension("5px")
                }
            }
            if (!state.verificationRequested) {
                styledDiv {
                    button(type = ButtonType.submit) {
                        +"Войти"
                        attrs {
                            disabled = !state.phoneMatchesPattern
                            onClickFunction = {
                                signIn()
                            }
                        }
                    }
                    css {
                        textAlign = TextAlign.center
                    }
                }
            } else {
                child(VerificationCodeComponent::class) {
                    attrs {
                        phone = state.phone!!
                        errorHandler = props.errorHandler
                        accessTokenHolder = props.accessTokenHolder
                        userClient = props.userClient
                    }
                }
            }
            css {
                width = LinearDimension("200px")
                margin = "auto"
            }
        }
    }

    private fun signIn() = GlobalScope.launch {
        try {
            val phone = state.phone ?: return@launch
            props.userClient.signIn(SignIn(phone))
            setState(State(phone, verificationRequested = true))
        } catch (e: ClientException) {
            props.errorHandler.handle(this, e)
        }
    }

    interface Props : RProps {
        var errorHandler: ErrorHandler<Any>
        var accessTokenHolder: AccessTokenHolder
        var userClient: UserClient
    }

    data class State(
        val phone: String?,
        val verificationRequested: Boolean = false,
    ) : RState {

        val phoneMatchesPattern: Boolean =
            if (phone == null) {
                false
            } else {
                PHONE_PATTERN.matches(phone)
            }

        companion object {
            private val PHONE_PATTERN = Regex("79\\d{9}")
        }
    }
}
