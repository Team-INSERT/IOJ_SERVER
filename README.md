![배너](https://i.postimg.cc/yYYgFhXb/A4-3-IOJ-brochure.png)

# 실시간 아이템 사용으로 경쟁하는 온라인 저지 서비스(IOJ)

실시간으로 아이템을 사용해 경쟁할 수 있는 온라인 저지 서비스입니다.  
알고리즘 대회라는 경쟁 요소와 더불어, 상대방에게 영향을 미칠 수 있는 ‘아이템’ 기능을 추가하여 재미와 몰입도를 높이고 진입장벽을 낮추었습니다.

[운영중인 서비스 바로가기](https://www.insert-ioj.com)  
[API 명세서 바로가기](https://roomy-python-d69.notion.site/API-102a3c7b34cf8102934fd19bdfc32254?pvs=4)

## 세부 성과
2024.09 | 교내 형성평가 수업 활용  
2024.09 | AI･SW 프로젝트 공모전  
2024.09 | 부산 백스코 AI 박람회 부스 운영  
2024.10 | 40+명 교내 대회 진행  
2024.11 | 교내 동아리 경진대회 장려상  
2024.11 | 대한민국 고등학생 소프트웨어 개발 공모전 금상  
2024.12 | SOFT WAVE 부스 운영  

<img src="https://i.postimg.cc/pVQT0Pf7/image.png" width="49%" />

### 40+명 규모 알고리즘 대회 운영

**서비스를 이용하여 50분간 대회를 진행함**
- 문제를 일부분을 직접 제작하여 대회에 출제 하였으며, 참가자들의 실력을 적절히 평가할 수 있도록 난이도와 유형을 조율
- 대회 종료 후 설문조사를 통해 69.2%의 만족도를 기록

**대회 종료 후 회고**
- 설문조사와 회고를 통하여 UI/UX, 반복 제출, 순위 계산 등 불편 사항을 취합
- 회고 및 분석을 통해 도출한 문제점들을 바탕으로, 동시성 처리 안정화, 순위 계산 쿼리 개선 등 백엔드 성능 개선 작업을 수행

## 기능

### 문제 관리
**기능 :** 문제 출제/수정/삭제, 난이도 분류, 테스트케이스 관리  
**설명 :** 관리자 페이지를 통해 문제를 생성하고, 난이도 및 테스트케이스를 설정할 수 있습니다. 이는 채점 시스템과 연동되어 문제 정답 여부를 판별합니다.

### 대회 운영
**기능 :** 대회 생성/수정, 대회별 문제 구성, 실시간 순위 집계  
**설명 :** 운영자는 대회 정보를 등록하고 문제를 선택하여 대회를 구성할 수 있으며, 참가자의 제출 기록을 기반으로 실시간 순위를 집계합니다.

### 아이템전
**기능 :** 실시간 아이템 발동, 공격/방어 로직, 일괄지급  
**설명 :** STOMP 기반 통신을 활용해 아이템 사용 시 타 유저에게 효과가 즉시 반영됩니다. 공격/방어 서버에서 검증되며, 30초 간격으로 전체 유저에게 아이템을 일괄 지급합니다.

### 문제 제출 및 채점
**기능 :** 샌드박스 내 컴파일 및 실행, 결과 수집 및 검증  
**설명 :** 사용자가 제출한 코드는 격리된 컨테이너 기반 샌드박스 환경에서 컴파일 및 실행되며, 실행 결과는 채점 서버에 전송되어 각 테스트케이스 기준에 따라 정답 여부가 평가됩니다.

## 시스템 아키텍처

![아키텍처](https://i.postimg.cc/g2BLNwkH/image.png)

### 코드 실행 플랫폼 백엔드 구현
- Java 17 & Spring Boot 3.3.0 기반, Spring Security로 인증/인가 구현
- Docker 기반의 샌드박스 환경을 구성하여 사용자 소스 코드 컴파일 격리 실행
- STOMP 프로토콜을 활용해 사용자 간 실시간 아이템 상호작용 구현
- 다형성을 활용한 언어 확장 구조로 C, Java, Python 등의 코드 실행 지원
### 개발 환경 구성
- 비용 절감과 운영 환경 유지를 위해 온프레미스 환경 구성
- 대량 트래픽에 대비해 ELB를 활용한 클러스터 구성
- Spring Cloud Config를 통해 환경별 설정을 분리하고 동적으로 관리
### CI/CD
- 배포에 대한 시간 소요를 줄이기 위해 Jenkins를 활용해 파이프라인 구성
- 일관된 방식으로 배포를 수행하여 운영 환경에 대한 신뢰성과 안정성 확보
### 모니터링 및 부하 테스트
- 알고리즘 대회에 대비하기 위해 JMeter를 활용하여 부하 테스트 진행
- 서비스 모니터링을 위해 Prometheus로 매트릭을 수집하고, Grafana대시보드를 통해 시각화

### 샌드박스 환경에서 컴파일 및 실행 로직 구현

<p float="left">
  <img src="https://i.postimg.cc/wMNLyDKB/image.png" width="49%" />
  <img src="https://i.postimg.cc/CLYzH3k7/insert-Stack-Flow.jpg" width="49%" />
</p>

<p align="center">
  <b>As-Is</b> (왼쪽) &nbsp;&nbsp;&nbsp;&nbsp; <b>To-Be</b> (오른쪽)
</p>

### 다양한 언어를 실행하기 위하여 추상화를 사용

<img src="https://i.postimg.cc/k43nMSys/image.png" width="75%" />

**Execution 추상 class를 만들고 각 언어의 자식들을 상속**
- 여러 언어의 확장성을 가지게 됨
- 언어의 종류를 구별하지 않고 다형성을 사용하여 코드의 가독성을 향상

**ExecutionFactory를 만들어 각 언어의 객체 생성을 편리하게 함**
- LanguageConfig를 이용하여 각 언어의 생성자를 registerExecution에 등록 후 ExecutionFactory를 사용하여 생성하는 객체의 Language를 확인 후 객체 생성

## ERD
![erd](https://i.postimg.cc/ryCVQ96c/IOJ-ERD.png)

## 화면 설계

|                                 메인 페이지                                 |                             문제 풀이 페이지                             |
|:----------------------------------------------------------------------:|:-----------------------------------------------------------------:|
|    <img width="329" src="https://i.postimg.cc/L4jggrx9/image.png"/>    | <img width="329" src="https://i.postimg.cc/cCZ0w86m/0002-1.png"/> |  
|                                 순위 페이지                                 |                             아이템 사용 화면                             |  
| <img width="329" src="https://i.postimg.cc/wBvCKQRZ/0003-blur-1.png"/> | <img width="329" src="https://i.postimg.cc/4d0FTCRh/0001-1.png"/> |

## 팀원
|                           Backend(팀장)                           |                            Frontend                             |                            Frontend                             |                            Designer                             |
|:---------------------------------------------------------------:|:---------------------------------------------------------------:|:---------------------------------------------------------------:|:---------------------------------------------------------------:|
| ![image](https://avatars.githubusercontent.com/u/127452485?v=4) | ![image](https://avatars.githubusercontent.com/u/128461588?v=4) | ![image](https://avatars.githubusercontent.com/u/128370837?v=4) | ![image](https://avatars.githubusercontent.com/u/119480957?v=4) |
|                [안예성](https://github.com/anys34)                 |               [김시연](https://github.com/kimsiyeon0223)                |             [김영은](https://github.com/winternuary)              |                [최성훈](https://github.com/seonghoon07)                |

