## 실습 목표
OAuth2.0 클라이언트와 스프링 시큐리티 6 프레임워크를 활용하여, 

신뢰할 수 있는 외부 사이트(구글, 네이버)로 부터 인증을 받고, 

전달 받은 유저 데이터를 활용하여 **JWT를 발급하고 인가**를 진행하는 방법.



---

## 구현
 - OAuth2 클라이언트 JWT방식을 구현하기 위한 가장 기본적인 뼈대 코드

 - OAuth2.0 코드 방식 인증을 활용

 - 인증 후 발급된 정보로 JWT를 발급, JWT는 단일 토큰으로 진행

<br>


### 버전 및 의존성
+ Spring boot 3.5.5
+ Spring Security 6.3.4
+ OAuth2 Client
+ Spring Data JPA
+ jjwt-api:0.12.3
+ jjwt-impl:0.12.3
+ jjwt-jackson:0.12.3
