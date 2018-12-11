pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install -DskipTests'
      }
    }
    stage('Testing') {
      steps {
        sh 'mvn test'
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