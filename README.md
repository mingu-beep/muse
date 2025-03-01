# Muse

📌 프로젝트 소개

사용자가 자신의 음악 취향을 공유할 수 있는 플랫폼입니다.
노래 제목, 아티스트, 곡의 분위기, 그리고 개인적인 코멘트를 포함한 게시물을 작성할 수 있습니다.

🚀 주요 기능

사용자 인증 및 권한: 회원가입, 로그인, 로그아웃

게시물 작성: 노래 제목, 아티스트, 분위기, 코멘트 입력 가능

게시물 조회: 다른 사용자의 음악 추천 확인 가능

관리자 기능: 부적절한 게시물 삭제 및 사용자 관리

검색 및 필터링: 특정 분위기나 아티스트를 기준으로 게시물 검색 가능

🛠️ 기술 스택

Backend: Spring Boot, Spring Security, JPA (Hibernate), MariaDB

Frontend: Thymeleaf

배포: (추후 추가 예정)

📂 프로젝트 구조

```dtd
폴더 PATH의 목록입니다.
볼륨 일련 번호는 C420-2755입니다.
C:.

muse
|
│  MuseApplication.java
│  
├─config
│  ├─jwt
│  └─oauth
│          
├─domain
│  ├─comment
│  ├─handler
│  │  ├─aop
│  │  └─ex
│  ├─likes
│  ├─mood
│  ├─music
│  ├─refreshToken
│  └─user
│          
├─service
│      
├─util
└─web
    ├─controller
    └─dto
        ├─comment
        ├─mood
        ├─music
        └─user
```


🔧 설치 및 실행 방법

1. 프로젝트 클론

git clone https://github.com/mingu-beep/muse.git
cd muse


🛠️ 향후 개선 사항
1. 게시글 및 댓글 CRUD
- 페이징 및 정렬 기능 (Spring Data JPA Pageable 활용)
2. 검색 및 필터링 기능
- QueryDSL / Specification을 활용한 동적 탐색
- Elastic Search를 활용한 고급 검색
3. 실시간 기능
- WebSocket을 이용한 실시간 알림 (댓글, 좋아요 등)
- Redis를 이용한 실시간 데이터 처리
4. 대용량 데이터 처리 및 비동기 처리

📜 라이선스

