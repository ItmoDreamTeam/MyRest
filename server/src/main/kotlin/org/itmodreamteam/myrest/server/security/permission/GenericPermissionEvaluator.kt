package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class GenericPermissionEvaluator(domainPermissionEvaluators: Set<DomainPermissionEvaluator>) : PermissionEvaluator {

    private val domainPermissionEvaluators: Map<String, DomainPermissionEvaluator> = domainPermissionEvaluators
        .groupBy(DomainPermissionEvaluator::targetType)
        .mapValues { (targetType, evaluators) ->
            if (evaluators.size == 1) evaluators.first()
            else throw RuntimeException("Duplicate DomainPermissionEvaluator for target $targetType")
        }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        if (authentication is UserAuthentication && targetId is Long && permission is String) {
            val evaluator = domainPermissionEvaluators[targetType] ?: return false
            return evaluator.hasPermission(authentication, targetId, permission)
        }
        return false
    }

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        return false
    }
}
