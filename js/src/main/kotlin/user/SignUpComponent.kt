package user

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.user.SignUp
import org.itmodreamteam.myrest.shared.user.UserClient
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.label
import security.AccessTokenHolder
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledInput

class SignUpComponent(props: Props) : RComponent<SignUpComponent.Props, SignUpComponent.State>(props) {

    init {
        state = State(null, null, null)
    }

    override fun RBuilder.render() {
        styledDiv {
            label {
                +"Имя:"
            }
            styledInput {
                attrs {
                    readonly = state.verificationRequested
                    onInputFunction = {
                        val firstName = (it.target as HTMLInputElement).value
                        setState(State(firstName, state.lastName, state.phone))
                    }
                }
                css {
                    +SignUpStyles.field
                }
            }

            label {
                +"Фамилия:"
            }
            styledInput {
                attrs {
                    readonly = state.verificationRequested
                    onInputFunction = {
                        val lastName = (it.target as HTMLInputElement).value
                        setState(State(state.firstName, lastName, state.phone))
                    }
                }
                css {
                    +SignUpStyles.field
                }
            }

            label {
                +"Номер телефона:"
            }
            styledInput {
                attrs {
                    readonly = state.verificationRequested
                    placeholder = "79123456789"
                    onInputFunction = {
                        val phone = (it.target as HTMLInputElement).value
                        setState(State(state.firstName, state.lastName, phone))
                    }
                }
                css {
                    +SignUpStyles.field
                }
            }

            if (!state.verificationRequested) {
                styledDiv {
                    button(type = ButtonType.submit) {
                        +"Зарегистрироваться"
                        attrs {
                            disabled = !state.fieldsValid
                            onClickFunction = {
                                signUp()
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

    private fun signUp() = GlobalScope.launch {
        try {
            val firstName = state.firstName ?: return@launch
            val lastName = state.lastName ?: return@launch
            val phone = state.phone ?: return@launch
            props.userClient.signUp(SignUp(firstName, lastName, phone))
            setState(State(firstName, lastName, phone, verificationRequested = true))
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
        val firstName: String?,
        val lastName: String?,
        val phone: String?,
        val verificationRequested: Boolean = false,
    ) : RState {

        private val phoneMatchesPattern: Boolean =
            if (phone == null) {
                false
            } else {
                PHONE_PATTERN.matches(phone)
            }

        val fieldsValid: Boolean = !firstName.isNullOrBlank() && !lastName.isNullOrBlank() && phoneMatchesPattern

        companion object {
            private val PHONE_PATTERN = Regex("79\\d{9}")
        }
    }

    object SignUpStyles : StyleSheet("signUp", isStatic = true) {
        val field by css {
            width = LinearDimension.fillAvailable
            marginBottom = LinearDimension("5px")
        }
    }
}
