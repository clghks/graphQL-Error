package com.clghks.graphql.configuration

import graphql.GraphQL
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.AsyncSerialExecutionStrategy
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfiguration {
    @Bean
    fun graphQL(schema: GraphQLSchema?): GraphQL? {
        return GraphQL.newGraphQL(schema)
            .queryExecutionStrategy(AsyncExecutionStrategy(CustomDataFetcherExceptionHandler()))
            .mutationExecutionStrategy(AsyncSerialExecutionStrategy(CustomDataFetcherExceptionHandler()))
            .build()
    }
}