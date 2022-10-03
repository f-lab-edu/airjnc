AirBnB와 같은 숙박 예약 서비스를 개발하는 프로젝트입니다.
    
# 사용 기술
### Back
- Java, Spring Boot, Spring MVC, MyBatis, Gradle
### DB
- MySQL, Redis
### Other
- Jenkins, Docker

# Use Cases

- [Github Wiki](https://github.com/f-lab-edu/airjnc/wiki/Use-Cases)에 작성했습니다.

# 브랜치 전략
### BRANCH
    - master
    - feature/#(ISSUE_NUMBER)
    - chore/#(ISSUE_NUMBER)
### COMMIT
    - feature#(ISSUE_NUMBER):MESSAGE
    - refactor#(ISSUE_NUMBER):MESSAGE
    - chore#(ISSUE_NUMBER):MESSAGE
    - hotfix#(ISSUE_NUMBER):MESSAGE

# 전체 구조
![architecture drawio](https://user-images.githubusercontent.com/41284492/191675159-eea72ada-a76b-450c-9769-8cf7f520a008.png)

### 서브넷 구성 및 Security Group 설정
1. Spring Server
    - Public Subnet
    - 누구나 웹 요청 가능
    - SSH 통신[22 포트]은 설정해둔 IP로만 가능
2. Jenkins Server
    - Public Subnet
    - 8080 포트에 대해서 HTTP 요청 뚫어놓음 -> Github 에서 hook 요청을 해야하기 문
    - SSH 통신[22 포트]은 설정해둔 IP로만 가능
3. DB
    - Private Subnet
    - 3306 포트에 대해서 Spring Server만 요청 가능
4. Redis
    - Private Subnet
    - 6379 포트에 대해서 Spring Server만 요청 가능
### CI/CD 흐름
1. PR 요청
2. github에서 hook으로 Jenkins에게 요청
3. Jenkins는 Jenkinsfile을 스캔하여 파이프라인 진행
    1. Jar 빌드
    2. 단위 테스트
    3. 통합 테스트
    4. 도커 이미지 생성
    5. 생성된 도커 이미지 허브에 푸시
    6. Jenkins Server가 SSH를 통해 Spring Server에 접근하여, hub에 올라가져있는 docker image를 가져온 후, 실행
    7. 슬랙으로 결과 알림
4. Github PR 화면에서 파이프라인 성공 여부 출력
    - 성공시, Merge 가능
    - 실패시, Merge Block

# DB ERD

![Screen Shot 2022-09-14 at 11 45 05 AM](https://user-images.githubusercontent.com/41284492/191655860-f0b6904a-0d91-4d77-a25f-a8bc952a2790.png)
