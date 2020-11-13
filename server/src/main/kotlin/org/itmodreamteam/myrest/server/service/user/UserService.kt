package org.itmodreamteam.myrest.server.service.user

import org.itmodreamteam.myrest.shared.user.SignIn
import org.itmodreamteam.myrest.shared.user.SignUp

interface UserService {

    fun signUp(signUp: SignUp)

    fun signIn(signIn: SignIn)
}
