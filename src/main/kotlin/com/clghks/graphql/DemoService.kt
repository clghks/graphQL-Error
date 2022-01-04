package com.clghks.graphql

import com.clghks.graphql.model.User
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Service

@Service
@GraphQLApi
class DemoService {
    @GraphQLQuery(name = "getUser", description = "사용자 조회")
    fun getUser(): User {
        return User(id = 1, name = "최치환")
    }

    @GraphQLQuery(name = "getErrorUser", description = "사용자 조회 실패")
    fun getErrorUser(id: Long): User {
        throw Exception("사용자 조회 실패")
    }
}