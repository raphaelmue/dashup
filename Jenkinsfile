pipeline {
  agent any
  tool name: 'Xvfb', type: 'org.jenkinsci.plugins.xvfb.XvfbInstallation'
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install -DskipTests'
      }
    }
    stage('Testing') {
          steps {
          wrap([$class: 'Xvfb', screen: '1440x900x24']) {
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