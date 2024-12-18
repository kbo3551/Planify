# Planify
 <p>TODO 관리 프로젝트로 Spring Boot와 JPA를 활용하여 진행합니다.</p>
    <hr>
    <h2>기술 스택</h2>
    <ul>
        <li><strong>Framework:</strong> Spring Boot 2.7.18</li>
        <li><strong>Language:</strong> Java (JDK 11)</li>
        <li><strong>Persistence:</strong> JPA</li>
        <li><strong>Build Tool:</strong> Gradle</li>
        <li><strong>Database:</strong> H2 (In-Memory Database)</li>
    </ul>
    <hr>
    <h2>패키지 구조</h2>
    <pre>
com.planify.main
├── api
│   ├── login
│   ├── member
│   │   ├── application       // 비즈니스 로직 (Use Case)
│   │   ├── domain            // 핵심 도메인 모델 (Entity, Repository 인터페이스 등)
│   │   ├── infrastructure    // 기술적 구현 (JPA Repository 등)
│   │   ├── presentation      // API 요청/응답 처리 (Controller)
│   │   └── value             // 값 객체 및 enum 클래스
│   └── (다른 도메인 패키지 추가 가능)
├── config
│   ├── security              // 보안 설정 (Spring Security)
│   └── tiles                 // Tiles 뷰 레이아웃 설정
├── web                       // View를 렌더링하는 컨트롤러 (JSP 연동)
    </pre>
    <hr>
    <h2>설계 원칙</h2>
    <ul>
        <li><strong>Domain-Driven Design (DDD):</strong> 도메인 중심의 설계를 기반으로 각 도메인을 명확히 분리하여 비즈니스 로직을 관리합니다.</li>
        <li><strong>패키지 책임 분리:</strong> 각 레이어(application, domain, infrastructure, presentation)는 명확한 역할을 가지며 유지보수성과 확장성을 극대화합니다.</li>
        <li><strong>경량 데이터베이스:</strong> H2 데이터베이스를 활용하여 간편한 테스트 환경을 제공합니다.</li>
    </ul>

<br>![image](https://github.com/user-attachments/assets/fd8ab86f-12b5-44ff-9ec7-84aeb1c6b946)
<br>![image](https://github.com/user-attachments/assets/4d039294-dc0c-4fcc-a5e5-a4bcf19443bc)
