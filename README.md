# Search Service Application
블로그 검색을 구현한 멀티모듈 구조의 어플리케이션 입니다.
+ 지원기능
    + 블로그 검색하여 검색된 리스트 반환하는 API 제공
    + 인기검색어 리스트(탑 10) 반환하는 API 제공
    + DB로 관리되는 검색어 목록 반환하는 API 제공
## 환경
+ Spring Boot 3.x
+ Java 17
+ H2
+ JUnit5
+ Querydsl
+ Redis

## 구현 전략
* 인기검색어 Redis를 활용하여 구현
    * Redis를 사용한 이유로는 검색 내용을 매번 DB에 저장 및 Count를 증가시키는 것이 비효율적이라 생각되어 Redis의 ZSet 자료구조를 활용하여 구현하는 것이 낫다고 생각되어 사용하였습니다.
    * 또한 ZSet을 활용하며 중복된 데이터에 대한 효율적인 처리, Score를 통한 정렬 지원, 메모리 기반의 빠른 업데이트 & 저장 등의 이점도 가져갈 수 있다고 판단했습니다.
    * 단, Redis의 데이터가 유실될 수 있는 점과 블로그 검색을 진행하며 JPA를 추가적으로 활용하는 사례를 만들기 위해 Redis & DB 두가지 모두 활용하여 데이터를 적재하도록 구현하였습니다.
* 멀티모듈로 구성하여 모듈간 의존성 제약
* JPA를 이용시 Querydsl을 활용하여 동적 쿼리 및 페이징 처리를 진행하였습니다.

## Build & Run
* 실행 환경
    * 실행 시 마다 Schema.sql 의 ddl이 초기 셋팅되도록 구성되어 있습니다.
    * <b>!! docker-compose 를 통해 Redis 셋업이 필요합니다.</b>

0. 사전 준비
   ```
   root project 기준 app 하위의 ~/src/main/resources/application-local.yml 파일 내용 중 kakao api key, naver api key를 발급받아 입력해주세요.
   
   docker-compose Settings
   1. $ cd ~/ops
   
   2. $ docker-compose up -d
   ```
   
1. Build & Run
   ```
   DB Settings
   - 별도의 DB 셋팅이 필요없으며 application-local.yml의 설정값으로 H2가 실행 및 Console 접근 가능합니다.
   - id: username
   - pw: password
   - db: ~/pretask;
   - url: http://localhost:8080/h2-ui
   
   Springboot 실행
   $ ./gradlew bootRun -DSpring.profile.active=local
   or
   $ ./gradlew build 또는 bootJar
   ```
  
## API Description
해당 프로젝트는 Swagger를 통해 백엔드 테스트 가능합니다.<br>
ex.) http://localhost:8080/swagger-ui.html

##### - Require parameter *표시
|  NO |      API NAME | HTTP<br>method |                   API PATH |                         API PARAM |                                  DESC | 
|----:|--------------:|---------------:|---------------------------:|----------------------------------:|--------------------------------------:| 
|   1 |        블로그 조회 |            GET |            /v1/search/blog | *query <br>sort <br>page <br>size |    카카오 API 또는 네이버 API를 활용하여 블로그 검색 결과를 제공합니다.
|   2 | 인기검색어(탑10) 조회 |            GET | /v1/search/popular-keyword |                                 - | 인기검색어(많이 검색한 순위) 결과를 제공합니다.
|   3 |     검색어 목록 조회 |            GET |    /v1/search/keyword-list |              keyword <br>pageable |         현재까지 검색된 검색어 목록을 제공합니다.

## ToDo Work
- 검색 매크로 방지
    * 회원 기반의 검색만을 유효로 볼 것인지, IP 기반의 특정 시간내의 검색만을 유효하게 볼 것인지 등 정책적으로 어떤 검색이 유요한지를 정의하고 이를 검색 횟수로 지정해야 된다고 생각됩니다.
    * 현재는 검색 API 호출 시 마다 검색어에 대한 카운트를 증가시키고 있습니다.
- External API 테스트
    * 현재는 mockk를 활용하여 외부 API 호출을 mocking 하고 있습니다.
    * 다만, 별도의 테스트 서버등을 구성하여 실제로 API 호출을 진행하는 자동화 테스트가 필요한 상황도 대비해야 한다고 봅니다.
   
   
   
      
