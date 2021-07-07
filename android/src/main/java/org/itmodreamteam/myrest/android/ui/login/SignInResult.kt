package org.itmodreamteam.myrest.android.ui.login

import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.Profile

/**
 * Authentication result : success (user details) or error message.
 */
data class SignInResult(
    val success: Profile? = null,
    val error: Int? = null,
    val exception: ClientException? = null
)