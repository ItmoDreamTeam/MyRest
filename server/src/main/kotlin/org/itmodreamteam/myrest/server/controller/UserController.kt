package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.user.UserService
import org.itmodreamteam.myrest.shared.user.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getMe(userAuthentication: UserAuthentication): Profile {
        return userAuthentication.profile
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun update(patch: ProfilePatch): Profile {
        return userService.update(patch)
    }

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUp: SignUp) {
        userService.signUp(signUp)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody signIn: SignIn) {
        userService.signIn(signIn)
    }

    @PutMapping("/sessions")
    fun startSession(@RequestBody signInVerification: SignInVerification): ActiveSession {
        return userService.startSession(signInVerification)
    }
}
