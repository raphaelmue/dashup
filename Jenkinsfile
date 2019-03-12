pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install -DskipTests'
      }
    }
    wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', autoDisplayName: true, debug: true, displayNameOffset: 0, installationName: 'Xvfb', parallelBuild: true, screen: '1024x758x24', timeout: 25]) {
    stage('Testing') {
          steps {
            sh 'mvn test'
          }
      }
    }
    stage('Deploy') {
      when{
        branch 'deployment'
      }
      steps {
        sh 'mvn spring-boot:run'
      }
    }
  }
}