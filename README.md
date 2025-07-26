# Spring Advanced 프로젝트
JWT 기반의 보안 시스템, AOP를 활용한 로깅, 커스텀 인터셉터를 이용한 인가 처리, Jacoco를 통한 테스트 커버리지 측정


## 🚀 주요 기능

*   **인증 및 인가**: JWT(JSON Web Token)와 Bcrypt를 사용한 토큰 기반 인증 및 관리자 권한 인가 시스템을 구현했습니다.
*   **API 로깅**: Spring AOP를 활용하여 특정 Admin API의 요청 및 응답 본문을 실행 전후로 자동 로깅합니다.
*   **보안 필터링**: `Filter`와 `Interceptor`를 활용하여 역할(인증/인가)을 분리하고, Swagger UI 등 특정 경로는 보안 검사에서 제외합니다.
*   **API 문서 자동화**: SpringDoc OpenAPI를 사용하여 API 명세를 자동으로 생성하고, Swagger UI를 통해 테스트할 수 있습니다.

## 🛠️ 기술 스택 및 의존성

| 구분 | 기술 |
| :--- | :--- |
| **Framework** | Spring Boot 3.3.3, Spring Data JPA, Spring Web, Spring AOP |
| **Security** | JWT (jjwt 0.11.5), Bcrypt (at.favre.lib:bcrypt:0.10.2) |
| **Database** | H2 (메모리 DB), MySQL Connector |
| **API Docs** | SpringDoc OpenAPI (Swagger UI 2.6.0) |
| **Testing** | JUnit 5, Jacoco |
| **Build** | Gradle |
| **Utilities** | Lombok |

## 📖 API 문서 확인하기

애플리케이션 실행 후, 아래 URL을 통해 API 문서를 확인하고 직접 테스트해볼 수 있습니다.

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 🔐 보안 시스템 구현 상세

이 프로젝트의 보안 시스템은 `Filter`와 `Interceptor`의 역할을 명확히 분리하여 구현되었습니다.

1.  **인증 (`JwtFilter`)**:
    -   가장 먼저 모든 요청을 가로채서 `Authorization` 헤더의 JWT 유효성을 검사합니다.
    -   토큰이 유효하면, 토큰에서 추출한 사용자 정보(`userId`, `userRole` 등)를 `HttpServletRequest`의 속성(Attribute)에 담아 다음 단계로 전달합니다.

2.  **인가 (`AdminAuthInterceptor`)**:
    -   `WebConfig`에 등록되어 `/admin/**` 경로의 요청만 선택적으로 가로챕니다.
    -   `JwtFilter`가 담아준 `userRole` 속성을 확인하여, `ADMIN` 역할이 아닐 경우 `AccessDeniedException`을 발생시켜 접근을 차단합니다.

## 📝 AOP를 활용한 로깅

`LoggingAspect` 클래스를 통해 특정 컨트롤러의 API 실행 흐름을 추적합니다.

-   **대상**: `CommentAdminController`, `UserAdminController`의 모든 메소드
-   **기능**: `@Around` 어드바이스를 사용하여 메소드 실행 전에는 **요청 본문(Request DTO)**을, 실행 후에는 **응답 본문(Response DTO)**을 로그에 출력합니다. 이를 통해 API의 입출력을 명확하게 확인할 수 있습니다.

