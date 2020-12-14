package org.itmodreamteam.myrest.server.service.user

import org.itmodreamteam.myrest.shared.user.*

interface UserService {

    fun signUp(signUp: SignUp)

    fun signIn(signIn: SignIn)

    fun startSession(signInVerification: SignInVerification): ActiveSession

    fun verifySession(token: String): Profile
}
