pipeline {
  agent {
    node {
      label 'built-in'
    }

  }
  stages {
    stage('Build Jar') {
      steps {
        withGradle() {
          sh './gradlew clean bootJar'
        }

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
        sh './gradlew integrationTest'
        junit '**/build/test-results/integrationTest/*.xml'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t airjnc-app .'
      }
    }

    stage('Push Docker Image') {
      steps {
        sh 'docker image tag hanjn2842/airjnc-app:latest hanjn2842/airjnc-app:${BUILD_NUMBER}'
      }
    }

    stage('Deploy Docker Image') {
      steps {
        sh 'ssh -p 22 root@192.168.0.7 -T sh < /var/lib/jenkins/docker-deploy.sh'
      }
    }

  }
}