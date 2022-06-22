# 프로젝트 - AirJnC

- AirBnB와 같은 숙박 예약 서비스를 개발하는 프로젝트입니다.

### 사용 기술 및 환경

- Java
- Spring Boot
- Spring MVC
- Gradle
- MyBatis
- MySQL
- Redis
- Jenkins
- Docker

### 목표

- 단순 기능 구현에 그치지 않고, 부하 테스트를 통한 대용량 트래픽을 처리하기 위해 노력합니다.

# UI

# Github

- Github Flow 를 따른다.

### Branch Rule

- BRANCH
    - master
    - feature/#(ISSUE_NUMBER)
    - chore/#(ISSUE_NUMBER)
- COMMIT
    - feature#(ISSUE_NUMBER):MESSAGE
    - refactor#(ISSUE_NUMBER):MESSAGE
    - chore#(ISSUE_NUMBER):MESSAGE
    - hotfix#(ISSUE_NUMBER):MESSAGE

### 그 외 규칙

1. commit message 깔끔하게 유지 → 최대한 basic merge를 사용하지 말고, rebase를 사용
    - “Merge remote-tracking branch '...' into develop” 이런 내용을 commit 내력에 최대한 남기지 말자.
2. Github Issue 기반으로 프로젝트 진행하기
    - Issue Number 최대한 적극적으로 활용

### Process

1. Issue 등록
    - Projects 필수
2. 해당 Issue에 대한 PR을 Draft로 등록
    - Projects 필수
3. 해당 작업이 모두 완료되면 Draft 제거
4. 리뷰 받기
    - 리뷰를 받고 수정해야 할 작업이 있다면, PR Title에 "wip"를 추가하고 진행한다. 이후, 모두 완료되었으면 "wip" 제거
5. 리뷰 모두 승인날 경우, Merge

# Use Cases

- Github Wiki에 작성했습니다.

# DB ERD

![airjnc-ERD](https://user-images.githubusercontent.com/41284492/174721953-13a253bb-7ae5-4822-9999-2280bfc3cf0d.png)

# 아키텍처

### 배포 방식

# 기술적 이슈 관련 포스팅

- 한재남: [노션 링크](https://jnam.notion.site/cb6e110e5b3340f49ab3e568674a57db?v=2217c4920bf54590afc7f41e382b1746)로 대체합니다.
