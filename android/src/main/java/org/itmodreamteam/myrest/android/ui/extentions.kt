package org.itmodreamteam.myrest.android.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.itmodreamteam.myrest.shared.error.ClientException


/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun Fragment.hideKeyboard(view: View) {
    val inputMethodManager =
        ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showFail(exception: ClientException?) {
    if (exception != null && exception.errors.isNotEmpty()) {
        Toast.makeText(context, exception.errors[0].userMessage, Toast.LENGTH_SHORT).show()
    }
    Log.w(javaClass.name, exception)
}

fun Fragment.showFail(@StringRes errorString: Int, exception: ClientException?) {
    if (exception != null && exception.errors.isNotEmpty()) {
        Toast.makeText(context, exception.errors[0].userMessage, Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }
    Log.w(javaClass.name, exception)
}