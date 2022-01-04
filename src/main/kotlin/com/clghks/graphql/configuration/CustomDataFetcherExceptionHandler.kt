package com.clghks.graphql.configuration

import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import javax.validation.ConstraintViolationException

class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {
    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        handlerParameters.exception.printStackTrace()
        val errors = when (val exception = handlerParameters.exception) {
            is ConstraintViolationException -> {
                exception.constraintViolations.map { CustomGraphQLError(code = it.propertyPath.toString(),  errorMessage= it.message) }
            }
            else -> listOf(CustomGraphQLError(errorMessage = exception.message ?: "error"))
        }

        return DataFetcherExceptionHandlerResult.newResult()
            .errors(errors)
            .build()
    }
}