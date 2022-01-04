package com.clghks.graphql.model

import com.clghks.graphql.validator.CustomValid
import javax.validation.constraints.NotBlank

@CustomValid
data class User(
    val id: Long?,
    @get:NotBlank(message = "이름은 공백일 수 없습니다.")
    val name: String?,
    val age: Int = 0,
    val isAdult: Boolean = false
)