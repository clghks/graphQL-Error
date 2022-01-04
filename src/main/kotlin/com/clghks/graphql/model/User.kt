package com.clghks.graphql.model

import javax.validation.constraints.NotBlank

data class User(
    val id: Long?,
    @get:NotBlank(message = "이름은 공백일 수 없습니다.")
    val name: String?
)