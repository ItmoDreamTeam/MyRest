package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserAuthentication(val profile: Profile) : Authentication {

    override fun getName() = profile.id.toString()

    override fun getPrincipal() = profile

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val role = profile.role ?: return listOf(SimpleGrantedAuthority("ROLE_ACTIVITI_USER"))
        return listOf(SimpleGrantedAuthority("ROLE_$role"), SimpleGrantedAuthority("ROLE_ACTIVITI_USER"))
    }

    override fun getCredentials() = null

    override fun getDetails() = null

    override fun isAuthenticated() = true

    override fun setAuthenticated(isAuthenticated: Boolean) = Unit
}
