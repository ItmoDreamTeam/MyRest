package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.security.UserAuthentication

interface DomainPermissionEvaluator {

    val targetType: String

    fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean
}
