package org.itmodreamteam.myrest.android.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


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