# Planify

TODO 관리 프로젝트로 Spring Boot와 JPA를 활용하여 진행 및 CI/CD 통해 자동화된 빌드를 통한 배포환경 구성

---

## 기술 및 라이브러리

- **Framework**: Spring Boot 2.7.18
- **Language**: Java (JDK 11)
- **Persistence**: JPA
- **Build Tool**: Gradle
- **Database**: H2 (In-Memory Database)
- **Tech**: Spring Security
- **Tech**: OAuth2 (소셜로그인)
- **Tech**: GitHub Actions (CI/CD)
- **Tech**: Docker (CI/CD)

---

## 패키지 구조

```plaintext
com.planify.main
├── api
│   ├── login
│   ├── member
│   │   ├── application       // 비즈니스 로직
│   │   ├── domain            // 핵심 도메인 모델 (Entity, Repository 인터페이스 등)
│   │   ├── infrastructure    // 기술적 구현 (JPA Repository 등)
│   │   ├── presentation      // API 요청/응답 처리 (Controller)
│   │   └── value             // 값 객체 및 enum 클래스
│   └── (도메인 패키지 추가)
├── config
│   ├── security              // 보안 설정 (Spring Security)
│   └── tiles                 // Tiles 뷰 레이아웃 설정
│   └── (설정 패키지 추가)
├── web                       // View를 렌더링하는 컨트롤러 (JSP, Thymeleaf)
```
---

## 설계 원칙

- **Domain-Driven Design (DDD)**: 도메인 중심의 설계를 기반으로 각 도메인을 명확히 분리하여 비즈니스 로직을 관리 원칙
- **패키지 책임 분리**: 각 레이어(application, domain, infrastructure, presentation)는 명확한 역할을 가지며 유지보수성과 확장성을 극대화
- **경량 데이터베이스**: H2 데이터베이스를 활용하여 간편한 테스트 환경을 제공

---

## CI/CD

- **GitHub Actions**: main Branches push를 시작으로 자동으로 빌드 및 테스트가 수행 및 Docker를 통해 배포
- **Docker**: 컨테이너화된 애플리케이션으로 배포하며 여러 환경에서 일관된 실행을 보장.

---

## 이미지

![image](https://github.com/user-attachments/assets/fd8ab86f-12b5-44ff-9ec7-84aeb1c6b946)
![image](https://github.com/user-attachments/assets/4d039294-dc0c-4fcc-a5e5-a4bcf19443bc)
![image](https://github.com/user-attachments/assets/c2bd63c1-89eb-429a-a68e-792a96a4563a)
![image](https://github.com/user-attachments/assets/5961ac16-a780-48bb-a502-12c4d2afb554)


