# VYBZ Support Service

VYBZ 플랫폼의 후원, 멤버십, 정산 서비스를 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [이벤트 처리](#이벤트-처리)

## 🎯 개요

VYBZ Support Service는 다음과 같은 기능을 제공합니다:

-   **후원 서비스**: 버스커에게 V-Ticket을 사용한 후원 기능
-   **멤버십 서비스**: 버스커 구독 관리 및 멤버십 상태 조회
-   **정산 서비스**: 버스커의 후원금 정산 신청 및 관리
-   **이벤트 처리**: Kafka를 통한 비동기 이벤트 처리

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=for-the-badge)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database & Cache

-   **MySQL 8.0**: 정산, 멤버십, 후원 내역 데이터 저장
-   **MongoDB**: 후원 관련 실시간 데이터 저장

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 처리

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 후원 서비스 (`/api/v1/donation`)

-   **후원 실행**: V-Ticket을 사용한 버스커 후원
-   **후원 내역 조회**: 사용자별 후원 히스토리 조회
-   **후원받은 내역 조회**: 버스커별 받은 후원 내역 조회
-   **V-Ticket 관리**: 충전, 사용, 환불 처리

### 2. 멤버십 서비스 (`/api/v1/membership`)

-   **활성 멤버십 조회**: 구독중인 멤버십 목록 조회
-   **만료된 멤버십 조회**: 만료된 멤버십 목록 조회
-   **구독자 수 조회**: 버스커별 활성 구독자 수 조회
-   **구독 개수 조회**: 사용자별 구독한 버스커 수 조회
-   **구독 여부 확인**: 특정 버스커 구독 여부 확인

### 3. 구독 관리 (`/api/v1/subscription`)

-   **빌링키 등록**: 자동 결제를 위한 빌링키 발급
-   **구독 해지**: 자동 결제 해지

### 4. 정산 서비스 (`/api/v1/settlements`)

-   **정산 가능 금액 조회**: 버스커별 정산 가능 금액 조회
-   **정산 신청**: 정산 신청 처리
-   **정산 내역 조회**: 정산 신청 히스토리 조회
-   **정산 신청 수정**: 정산 신청 정보 수정

## 📁 프로젝트 구조

```
src/main/java/back/vybz/support_service/
├── common/                    # 공통 모듈
│   ├── config/               # 설정 클래스들
│   │   ├── MongoConfig.java
│   │   ├── SwaggerConfig.java
│   │   └── WebConfig.java
│   ├── dto/                  # 공통 DTO
│   │   ├── request/
│   │   └── response/
│   ├── entity/               # 공통 엔티티
│   └── exception/            # 예외 처리
├── support/                  # 후원 도메인
│   ├── application/          # 후원 서비스 로직
│   │   ├── DonationService.java
│   │   └── feign/           # Feign 클라이언트
│   ├── domain/              # 후원 도메인 모델
│   │   ├── mongo/           # MongoDB 엔티티
│   │   └── mysql/           # MySQL 엔티티
│   ├── dto/                 # 후원 DTO
│   ├── infrastructure/      # 후원 리포지토리
│   ├── presentation/        # 후원 컨트롤러
│   └── vo/                  # 후원 VO
├── membership/              # 멤버십 도메인
│   ├── application/         # 멤버십 서비스 로직
│   │   ├── MembershipService.java
│   │   ├── SubscribeService.java
│   │   └── feign/          # Feign 클라이언트
│   ├── domain/             # 멤버십 도메인 모델
│   ├── dto/                # 멤버십 DTO
│   ├── infrastructure/     # 멤버십 리포지토리
│   ├── presentation/       # 멤버십 컨트롤러
│   └── vo/                 # 멤버십 VO
├── settlement/             # 정산 도메인
│   ├── application/        # 정산 서비스 로직
│   ├── domain/            # 정산 도메인 모델
│   ├── dto/               # 정산 DTO
│   ├── infrastructure/    # 정산 리포지토리
│   ├── presentation/      # 정산 컨트롤러
│   └── vo/                # 정산 VO
└── kafka/                 # Kafka 이벤트 처리
    ├── config/            # Kafka 설정
    ├── consumer/          # 이벤트 컨슈머
    ├── event/             # 이벤트 모델
    └── producer/          # 이벤트 프로듀서
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/support-service/swagger-ui/index.html`
-   **API 그룹**:
    -   Support-Service: 후원 관련 API
    -   Membership-Service: 멤버십 관련 API
    -   Settlement-Service: 정산 관련 API
    -   Support-Subscription-Service: 구독 관련 API

### 주요 API 엔드포인트

#### 후원 API

-   `POST /api/v1/donation` - 후원 실행
-   `GET /api/v1/donation/user-history/{userUuid}` - 사용자 후원 내역 조회
-   `GET /api/v1/donation/busker-history/{buskerUuid}` - 버스커 후원받은 내역 조회

#### 멤버십 API

-   `GET /api/v1/membership/active/{userUuid}` - 활성 멤버십 조회
-   `GET /api/v1/membership/expired/{userUuid}` - 만료된 멤버십 조회
-   `GET /api/v1/membership/busker-count/{buskerUuid}` - 버스커 구독자 수 조회
-   `GET /api/v1/membership/user-count/{userUuid}` - 유저 구독 개수 조회
-   `GET /api/v1/membership/subscribed/{userUuid}/{buskerUuid}` - 구독 여부 확인

#### 구독 API

-   `POST /api/v1/subscription/save-billing-key` - 빌링키 등록
-   `DELETE /api/v1/subscription` - 구독 해지

#### 정산 API

-   `GET /api/v1/settlements/status/{buskerUuid}` - 정산 가능 금액 조회
-   `POST /api/v1/settlements/{buskerUuid}` - 정산 신청
-   `GET /api/v1/settlements/history/{buskerUuid}` - 정산 신청 내역 조회
-   `PUT /api/v1/settlements/{settlementsUuid}` - 정산 신청 수정

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MySQL 8.0
-   MongoDB
-   Kafka

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd support_service

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-support-service .

# Docker 컨테이너 실행
docker run -p 8088:8088 vybz-support-service
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정
-   `application-dev.yml`: 개발 환경 설정
-   `application-db.yml`: 데이터베이스 설정

### 환경 변수

```yaml
# 데이터베이스 설정
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  data:
    mongodb:
      uri: mongodb://${MONGO_USER}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/${MONGO_DB}

# Kafka 설정
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
    consumer:
      group-id: charge-payment-group
```

## 📡 이벤트 처리

### Kafka 이벤트

#### 수신 이벤트

-   **ChargePaymentEvent**: V-Ticket 충전 이벤트
-   **PaymentRefundEvent**: V-Ticket 환불 이벤트
-   **SubscriptionEvent**: 구독 완료 이벤트
-   **SubscriptionCancelEvent**: 구독 취소 이벤트

#### 발행 이벤트

-   **SubscribeCountEvent**: 구독 수 변경 이벤트
-   **TicketChangedEvent**: V-Ticket 수량 변경 이벤트

### 이벤트 컨슈머

-   `PaymentChargeEventConsumer`: 결제 충전/환불 이벤트 처리
-   `SubscriptionEventConsumer`: 구독/해지 이벤트 처리

### 이벤트 프로듀서

-   `SubscribeCountEventProducer`: 구독 수 이벤트 발행
-   `VTicketKafkaEventProducer`: V-Ticket 변경 이벤트 발행

### Kafka 토픽

-   `create-payment-confirm`: 결제 승인 이벤트
-   `update-payment`: 결제 환불 이벤트
-   `subscription-completed`: 구독 완료 이벤트
-   `cancel-subscription`: 구독 취소 이벤트
-   `subscribe-count-events`: 구독 수 변경 이벤트
-   `create-ticket-changed`: V-Ticket 변경 이벤트

## 🏗 아키텍처

### 도메인 주도 설계 (DDD)

-   **Domain Layer**: 비즈니스 로직과 엔티티
-   **Application Layer**: 서비스 로직과 유스케이스
-   **Infrastructure Layer**: 외부 시스템 연동
-   **Presentation Layer**: API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Circuit Breaker**: Feign Client를 통한 외부 서비스 호출
-   **Event-Driven**: Kafka를 통한 비동기 이벤트 처리

### 데이터베이스 설계

-   **MySQL**: 정산, 멤버십, 후원 내역 등 영속성 데이터
-   **MongoDB**: 후원 관련 실시간 데이터 및 로그

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 외부 서비스 연동

#### Feign Client

-   `PaymentFeignClient`: 결제 서비스 연동
-   `UserProfileFeignClient`: 사용자 프로필 서비스 연동
-   `BuskerProfileFeignClient`: 버스커 프로필 서비스 연동
-   `FeedReadFeignClient`: 피드 서비스 연동
-   `LiveFeignClient`: 라이브 서비스 연동

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Support Service** - 버스커와 팬을 연결하는 후원 플랫폼
