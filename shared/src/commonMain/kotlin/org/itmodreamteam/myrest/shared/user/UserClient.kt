package org.itmodreamteam.myrest.shared.user

import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalStdlibApi
interface UserClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getMe(): Profile

    @Throws(CancellationException::class, ClientException::class)
    suspend fun signUp(signUp: SignUp)

    @Throws(CancellationException::class, ClientException::class)
    suspend fun signIn(signIn: SignIn)

    @Throws(CancellationException::class, ClientException::class)
    suspend fun startSession(signInVerification: SignInVerification): ActiveSession
}
