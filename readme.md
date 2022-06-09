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

### Branch Rule

[Git Flow와 자주 사용되는 Git 명령어들](https://www.holaxprogramming.com/2018/11/01/git-commands/)

[우린 Git-flow를 사용하고 있어요 | 우아한형제들 기술블로그](https://techblog.woowahan.com/2553/)

| Branch Name            | Description                                                                                                                                            |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| master                 | 제품으로 출시될 수 있는 브랜치                                                                                                                                      |
| develop                | 다음 출시 버전을 개발하는 브랜치                                                                                                                                     |
| feat/#<ISSUE_NUMBER>   | 기능을 개발하는 브랜치 <br/> - 브랜치가 생성되는 대상 → develop<br/> - merge 대상 → develop                                                                                  |
| release                | 이번 출시 버전을 준비하는 브랜치 <br/>- 브랜치가 생성되는 대상 → develop<br/> - merge 대상 → develop, master                                                                     |
| hotfix/#<ISSUE_NUMBER> | 배포 이후에 생긴 치명적인 버그는 즉시 해결해야 하기 때문에, 문제가 생기면 `master` 브랜치에 만들어둔 tag로 부터 긴급수정을 위한 브랜치를 생성한다.<br/>- 브랜치가 생성되는 대상 → master<br/>- merge 대상 → develop, master |

### Commit Rule

[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

[Semantic Commit Messages](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)

1. `feat(#<ISSUE_NUMBER>): MESSAGE`

    ```
    feat(#3): 로그인 기능
    
    - 이메일, 비밀번호 로그인 기능 추가
    - 세션 기반
    ```

    - new feature for the user, not a new feature for build script
2. `hotfix(#<ISSUE_NUMBER>): MESSAGE`
    - bug fix for the user, not a fix to a build script
3. `refactor(#<ISSUE_NUMBER>): MESSAGE`
    - refactoring production code, eg. renaming a variable
    - 코드 리팩토링
4. `test(#<ISSUE_NUMBER>): MESSAGE`
    - adding missing tests, refactoring tests; no production code change
    - 테스트 코드 작성
5. `chore(#<ISSUE_NUMBER>): MESSAGE`
    - updating grunt tasks etc; no production code change
    - **(코드변경없음)**빌드업무수정, 패키지 매니저 수정, 환경설정, 기타등등

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

# DB ERD

# 아키텍처

### 배포 방식

# 기술적 이슈
