package org.itmodreamteam.myrest.android.ui.login

/**
 * Data validation state of the login form.
 */
data class SignInFormState(
    val phoneError: Int? = null,
    val codeError: Int? = null,
    val isDataValid: Boolean = false
)