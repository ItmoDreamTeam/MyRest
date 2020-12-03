package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.user.UserService
import org.itmodreamteam.myrest.shared.user.ActiveSession
import org.itmodreamteam.myrest.shared.user.SignIn
import org.itmodreamteam.myrest.shared.user.SignInVerification
import org.itmodreamteam.myrest.shared.user.SignUp
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUp: SignUp) {
        userService.signUp(signUp)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody signIn: SignIn) {
        userService.signIn(signIn)
    }

    @PutMapping("/sessions/verification")
    fun startSession(@RequestBody signInVerification: SignInVerification): ActiveSession {
        return userService.startSession(signInVerification)
    }
}
