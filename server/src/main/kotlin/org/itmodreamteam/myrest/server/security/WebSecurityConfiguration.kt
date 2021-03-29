package org.itmodreamteam.myrest.server.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfiguration(
    private val authenticationManager: AuthenticationManager
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors()
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(STATELESS)
        http.oauth2ResourceServer().opaqueToken().authenticationManager(authenticationManager)
    }
}
