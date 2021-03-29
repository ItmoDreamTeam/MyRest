package org.itmodreamteam.myrest.shared.error

abstract class ErrorHandler<CONTEXT> {

    fun handle(context: CONTEXT, exception: ClientException) {
        handle(context, exception) { errors -> handleServerError(context, errors) }
    }

    fun handle(context: CONTEXT, exception: ClientException, handler: (errors: List<ServerError>) -> Unit) {
        when (exception.type) {
            ClientException.Type.UNAUTHENTICATED -> handleUnauthenticatedError(context)
            else -> handler(exception.errors)
        }
    }

    protected abstract fun handleUnauthenticatedError(context: CONTEXT)

    protected abstract fun handleServerError(context: CONTEXT, errors: List<ServerError>)
}
