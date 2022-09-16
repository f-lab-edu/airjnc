pipeline {
  agent any

  stages {
    stage('Start') {
      steps{
        slackSend (teamDomain: 'f-lab-community',
                    tokenCredentialId: 'airjnc-slack-accesstoken',
                    color: '#FFFF00',
                    message: "[🚀 Start] Job *${env.JOB_NAME}__${env.BUILD_NUMBER} (${env.BUILD_URL})* is Started")
      }
    }

    stage('Build Jar') {
      steps {
        withGradle() { sh './gradlew clean bootJar' }
      }
    }

    stage('Unit Test') {
      steps {
        sh './gradlew test'
        junit '**/build/test-results/test/*.xml'
      }
    }

    stage('Integration Test') {
      steps {
        sh 'docker-compose -f docker/docker-compose.yml up -d'
        sh './gradlew integrationTest'
        junit '**/build/test-results/integrationTest/*.xml'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          app = docker.build("hanjn2842/airjnc")
        }
      }
    }

    stage('Push Docker Image') {
      steps {
        script {
          docker.withRegistry("https://registry.hub.docker.com", "docker-hub"){
                    app.push("${BUILD_NUMBER}")
                    app.push("latest")
                  }
        }
      }
    }

    stage('Deploy Docker Image') {
      steps {
        sshPublisher(
          continueOnError: false, failOnError: true,
          publishers: [
            sshPublisherDesc(
              configName: 'web-server',
              verbose: true,
              transfers: [
                sshTransfer(
                  execCommand: 'sh /app/scripts/deploy-app', // 젠킨스 서버가 아니라, ssh를 통하여 접속한 서버에 파일이 있어야 함
                  remoteDirectory: '',
                  removePrefix: '',
                  sourceFiles: ''
                  )
                ],
              )
          ]
        )
      }
    }
  } // stages
  post {
    always{
      cleanWs()
      sh 'docker-compose -f docker/docker-compose.yml down'
    }
    success {
      slackSend (teamDomain: 'f-lab-community',
                  tokenCredentialId: 'airjnc-slack-accesstoken',
                  color: '#00FF00',
                  message: "[📦 success] Job *${env.JOB_NAME}__${env.BUILD_NUMBER} (${env.BUILD_URL})* is Successfully done")
    }
    failure {
      slackSend (teamDomain: 'f-lab-community',
                  tokenCredentialId: 'airjnc-slack-accesstoken',
                  color: '#FF0000',
                  message: "[😭 Failed] Job *${env.JOB_NAME}__${env.BUILD_NUMBER} (${env.BUILD_URL})* is failed")
    }
  }
}
