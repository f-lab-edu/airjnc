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
        app = docker.build("hanjn2842/airjnc")
      }
    }

    stage('Push Docker Image') {
      steps {
        docker.withRegistry("https://registry.hub.docker.com", "docker-hub"){
          app.push("${BUILD_NUMBER}")
          app.push("latest")
        }
      }
    }

    stage('Deploy Docker Image') {
      steps {
        sh 'ssh -p 22 root@192.168.0.7 -T sh < /var/lib/jenkins/docker-deploy.sh'
      }
    }

  }
}
