package org.itmodreamteam.myrest.android

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.error.ServerError

class AndroidErrorHandler : ErrorHandler<Context>() {

    override fun handleUnauthenticatedError(context: Context) {
        Log.i(javaClass.name, "401 handling")
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    override fun handleServerError(context: Context, errors: List<ServerError>) {
        Log.i(javaClass.name, "Default server error handling: $errors")
        if (errors.isNotEmpty()) {
            val error = errors.first()
            val toast = Toast.makeText(context, error.userMessage, Toast.LENGTH_LONG)
            toast.show()
        }
    }
}
