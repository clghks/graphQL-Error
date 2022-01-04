package com.clghks.graphql.configuration

import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation

class CustomGraphQLError(private val errorMessage: String, private val code: String = "", private val detailMessage: String = ""): GraphQLError {
    override fun getMessage(): String {
        return errorMessage
    }

    override fun getLocations(): MutableList<SourceLocation> {
        return mutableListOf()
    }

    override fun getErrorType(): ErrorClassification? {
        return null
    }

    override fun getExtensions(): Map<String, Any> {
        return mapOf<String, Any>("code" to code, "detailMessage" to detailMessage)
    }
}