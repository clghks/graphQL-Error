package com.clghks.graphql.validator

import com.clghks.graphql.model.User
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CustomValidator: ConstraintValidator<CustomValid, User> {
    override fun isValid(value: User, context: ConstraintValidatorContext): Boolean {
        if (value.isAdult && value.age < 18) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("나이 입력이 잘못되었습니다.").addConstraintViolation()
            return false
        }
        return true
    }
}