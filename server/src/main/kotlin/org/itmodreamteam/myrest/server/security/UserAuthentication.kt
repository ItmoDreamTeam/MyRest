package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(val profile: Profile) : Authentication {

    override fun getName(): String = "${profile.id}"

    override fun getPrincipal(): Profile = profile

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun getCredentials(): Any? = null

    override fun getDetails(): Any? = null

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) = Unit
}
