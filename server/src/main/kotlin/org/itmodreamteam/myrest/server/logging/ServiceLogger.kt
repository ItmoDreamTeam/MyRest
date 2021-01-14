package org.itmodreamteam.myrest.server.logging

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ServiceLogger {

    private val log = LoggerFactory.getLogger(javaClass)

    @Pointcut("@within(org.springframework.stereotype.Service)")
    fun serviceMethods() = Unit

    @Before("serviceMethods()")
    fun logStartOfInvocation(joinPoint: JoinPoint) {
        val method = joinPoint.signature.toShortString()
        val arguments = joinPoint.args.joinToString()
        val methodWithArguments = method.replace("..", arguments)
        log.info(methodWithArguments)
    }

    @AfterReturning(value = "serviceMethods()", returning = "result")
    fun logEndOfInvocation(joinPoint: JoinPoint, result: Any?) {
        val method = joinPoint.signature.toShortString()
        log.info("$method -> $result")
    }
}
