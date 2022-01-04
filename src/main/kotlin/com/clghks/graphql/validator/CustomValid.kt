package com.clghks.graphql.validator

import java.lang.annotation.Documented
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Documented
@Constraint(validatedBy = [CustomValidator::class])
@Target(AnnotationTarget.CLASS)
annotation class CustomValid(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)