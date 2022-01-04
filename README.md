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
