package org.itmodreamteam.myrest.shared.user

interface UserClient {

    suspend fun getMe(): Profile

    suspend fun signUp(signUp: SignUp)

    suspend fun signIn(signIn: SignIn)

    suspend fun startSession(signInVerification: SignInVerification): ActiveSession
}
