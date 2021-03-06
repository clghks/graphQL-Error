# GraphQL 에러 처리
## 사용한 라이브러리 
* GraphQL SPQR 라이브러리
```groovy
implementation("io.leangen.graphql:graphql-spqr-spring-boot-starter:0.0.6")
implementation("com.graphql-java-kickstart:graphql-java-tools:11.0.0")
```

## 사용자 조회 GraphQL API
```
{
  getUser {
    id
    name
  }
}
```
```
{
  "data": {
    "getUser": {
      "id": 1,
      "name": "최치환"
    }
  }
}
```

## 사용자 조회 실패 GraphQL API
```
{
  getErrorUser(id: 1) {
    id
    name
  }
}
```
```
{
  "errors": [
    {
      "message": "Exception while fetching data (/getErrorUser) : 사용자 조회 실패",
      "locations": [
        {
          "line": 2,
          "column": 3
        }
      ],
      "path": [
        "getErrorUser"
      ],
      "extensions": {
        "classification": "DataFetchingException"
      }
    }
  ],
  "data": {
    "getErrorUser": null
  }
}
```

## 에러 실패 메세지 수정 
```
{
  "errors": [
    {
      "message": "사용자 조회 실패",
      "locations": [],
      "extensions": {
        "code": "",
        "detailMessage": ""
      }
    }
  ],
  "data": {
    "getErrorUser": null
  }
}
```

## 사용자 등록 API
```
mutation {
  createUser(user: {
    id: 1
    name: ""
  }) {
    id
    name
  }
}
```
```
{
  "data": {
    "createUser": {
      "id": 1,
      "name": ""
    }
  }
}
```
## 사용자 등록 API Validation 추가
* 공백 체크 추가
```kotlin
data class User(
    val id: Long?,
    @get:NotBlank(message = "이름은 공백일 수 없습니다.")
    val name: String?
)
```
* GraphQL Service에 @Validated 추가 
* API @Valid 추가
```kotlin
@Service
@GraphQLApi
@Validated
class DemoService {
    @GraphQLQuery(name = "getUser", description = "사용자 조회")
    fun getUser(): User {
        return User(id = 1, name = "최치환")
    }

    @GraphQLQuery(name = "getErrorUser", description = "사용자 조회 실패")
    fun getErrorUser(id: Long): User {
        throw Exception("사용자 조회 실패")
    }

    @GraphQLMutation(name = "createUser", description = "사용자 등록")
    fun createUser(@Valid user: User): User {
        return user
    }
}
```
* 응답 결과
```
{
  "errors": [
    {
      "message": "이름은 공백일 수 없습니다.",
      "locations": [],
      "extensions": {
        "code": "createUser.user.name",
        "detailMessage": ""
      }
    }
  ],
  "data": {
    "createUser": null
  }
}
```

## CustomValidator 추가
* 정의 되어 있지 않음 Validator 을 정의하고 싶을 떄 사용 (ex: 나이 입력과 성인유무 입력을 받을 경우)
```kotlin
@Documented
@Constraint(validatedBy = [CustomValidator::class])
@Target(AnnotationTarget.CLASS)
annotation class CustomValid(
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

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

@CustomValid
data class User(
    val id: Long?,
    @get:NotBlank(message = "이름은 공백일 수 없습니다.")
    val name: String?,
    val age: Int = 0,
    val isAdult: Boolean = false
)
```
* API 호출
```
mutation {
  createUser(user: {
    id: 1
    name: "test"
    age: 10
    isAdult: true
  }) {
    id
    name
  }
}
```
* 오류 응답 
```
{
  "errors": [
    {
      "message": "나이 입력이 잘못되었습니다.",
      "locations": [],
      "extensions": {
        "code": "createUser.user",
        "detailMessage": ""
      }
    }
  ],
  "data": {
    "createUser": null
  }
}
```