# Lv2 Scheduler

## 📌 프로젝트 개요
일정관리 Web Application의 **BACKEND** 영역을 구현해 봅니다

실습을 통해 Spring Framwork 와 Spring Boot 를 이해하고

HTTP 요청과 응답, 코드의 기본 구조를 배워봅니다

## ✨기능
<details><summary><span style="font-weight:BOLD; font-size:medium">API 명세서</span></summary>

| schedule                                                                    | Method | URL                 | request                                                                                 | response                                                                                                                          | 상태 코드  |
|-----------------------------------------------------------------------------| ------ | ------------------- | --------------------------------------------------------------------------------------- |-----------------------------------------------------------------------------------------------------------------------------------| ------ |
| 일정 생성                                                                       | <span style="font-weight:BOLD">POST</span>   | /api/schedules      | 요청 body<br>{<br>"author":"작성자",<br>"title":"일정 제목",<br>"password":"qwer1234"<br>}       | 등록 정보                                                                                                                             | 200:OK |
| 일정 조회 (전체)+<br>파라미터로 해당 값만 조회 가능<br>(author,title,id,updateDate,createDate) | <span style="color:BLUE">GET</span>| /api/schedules      | 요청 param (?속성명=값)                                                                                | 가까운 updateDate 순으로 응답body (반드시 배열형태로 반환)                                                                                          | 200:OK |
| 일정 조회 (단일)                                                                  | <span style="color:BLUE">GET</span>      | /api/schedules/{id} | ~~요청 param~~                                                                                | 응답 body<br>{<br>"id":"id",<br>"author":"작성자",<br>"title":"일정 제목",<br>"createDate":"2025-03-20",<br>"updateDate":"2025-03-20"<br>} | 200:OK |
| 일정 수정 (모든 속성)                                                               | <span style="color:GREEN">PUT</span>    | /api/schedules/{id} | 요청 body<br>{<br>"author":"작성자 수정",<br>"title":"일정 제목 수정",<br>"password":"qwer1234"<br>} | 응답 body<br>{<br>"id":"id",<br>"author":"작성자 수정",<br>"title":"일정 제목 수정",<br>"createDate":"2025-03-20",<br>"updateDate":"요청날짜"<br>} | 200:OK |
| 일정 수정 (제목 속성)                                                               | PATCH  | /api/schedules/{id} | 요청 body<br>{<br>"title":"일정 제목 수정",<br>"password":"qwer1234"<br>}                       | 응답 body<br>{<br>"id":"id",<br>"author":"작성자",<br>"title":"일정 제목 수정",<br>"createDate":"2025-03-20",<br>"updateDate":"요청날짜"<br>}    | 200:OK |
| 일정 삭제                                                                       | <span style="color:RED">DELETE</span>| /api/schedules/{id} | 요청 body<br>{<br>"password":"qwer1234"<br>}                                                                                | \-                                                                                                                                | 200:OK |

</details>

- 일정 생성(일정 작성하기)
- 할일, 작성자명, 비밀번호, 작성/수정일을 저장
- 각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
- 최초 입력 시, 수정일은 작성일과 동일
- 선택한 일정 단건(ID를 통한)의 정보를 조회할 수 있습니다.


- PUT,PATCH,DELETE 의 수정,삭제의 경우 비밀번호가 동일해야 기능을 수행합니다

## 🗄데이터베이스 설계

<details><summary><span style="font-weight:BOLD; font-size:medium">ERD</span></summary>

![onealog](/assets/erdlv3.png)

</details>

## 📁 프로젝트 구조

<details><summary><span style="font-weight:BOLD; font-size:medium">구조</span></summary>

```
ScheduleProject-dev/
├── .gitattributes
├── .gitignore
├── build.gradle  # 의존성 및 라이브러리 관리
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── schedule.sql  # DB에서 사용한 query문 모음
├── settings.gradle
└── src/
    ├── main/
    │     └── java/com/example/scheduleproject/
    │          ┌──────────────────────────┘ 
    │          ├── ScheduleProjectApplication.java  # 메인 실행 파일
    │          ├── controller/
    │          │   └── ScheduleController.java  # HTTP 요청과 응답을 컨트롤
    │          ├── dto/
    │          │   ├── ScheduleRequestDto.java  # service layer간 정보 전달자 
    │          │   └── ScheduleResponseDto.java  # service layer간 정보 전달자
    │          ├── entity/
    │          │   └── Schedule.java  # 일정 Entity
    │          ├── repository/
    │          │   ├── JdbcTemplateScheduleRepository.java  # DB 와 Entity간 상호작용
    │          │   └── ScheduleRepository.java
    │          ├── service/
    │          │   ├── ScheduleService.java
    │          │   └── ScheduleServiceImpl.java  # 비지니스 로직을 수행
    │          └── resources/
    │                  └── application.properties  # mySQL 연결 정보 기입
    └── test/
         └── java/com/example/scheduleproject/
            ┌───────────────────────┘  
            └── ScheduleProjectApplicationTests.java
```
</details>


## 🛠️🚀🧪 기술 스택
- **Java**: 애플리케이션 구현 언어 - 17.x버전
- **IntelliJ IDEA**: 개발 환경
- **Spring**: Java 웹 어플리케이션 프레임워크
- **mySQL**: DataBase 구축 및 관리 - 8.x 버전
- **Git**: 버전 관리 및 브랜치 사용
- **PostMan**: HTTP 요청 및 응답 테스트 도구 

## ⚠️예외 처리 방식
- Index 접근이 있을 때 Optional 로 null 을 안정화

## 📝향후 개선 사항
- ~~수정과 삭제시 비밀번호검증 기능 추가~~
- ~~updatdDate 는 수정 완료시 자동 변경~~
- DB에서 작성자를 분리하여 관리하기
- 페이지네이션

## 🐞트러블슈팅
- DB에서 설정한 varchar 길이 넘어가면 500 error 가 뜨는것 유효성 검사하기
- 서비스레이어에서의 코드 흐름 파악 못해서 엉뚱한 대답 받음
- jdbc 쿼리를 작성할 때 ? 라는 prepared Statement 을 무시하면 DB에 1=1공격, drop 공격 가능