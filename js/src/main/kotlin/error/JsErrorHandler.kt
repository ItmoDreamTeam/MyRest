package error

import PreviousPageRedirection
import RootComponent.State.SIGN_IN
import kotlinx.browser.window
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.error.ServerError

class JsErrorHandler : ErrorHandler<Any>() {

    override fun handleUnauthenticatedError(context: Any) {
        PreviousPageRedirection.saveCurrentUri()
        window.location.hash = SIGN_IN.fragment
        window.location.reload()
    }

    override fun handleServerError(context: Any, errors: List<ServerError>) {
        console.log(errors)
        if (errors.isNotEmpty()) {
            val error = errors.first()
            window.alert(error.userMessage)
        }
    }
}
