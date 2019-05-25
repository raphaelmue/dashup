pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Preparing tests') {
            steps {
                sh 'mkdir -p ./de.dashup.model/src/main/resources/de/dashup/model/db/config'
                sh 'cp /var/lib/jenkins/workspace/dashup_database.conf ./de.dashup.model/src/main/resources/de/dashup/model/db/config/database.conf'
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Unit testing') {
             steps {
                sh 'mvn test -P unit-tests'
             }
        }
        stage('Testing in Chrome') {
            steps {
                wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', autoDisplayName: true, debug: true, displayNameOffset: 0, installationName: 'Xvfb', parallelBuild: true, screen: '1024x758x24', timeout: 25]) {
                    sh 'mvn test -P integration-tests,chrome-testing'
                }
            }
        }
        stage('Testing in Firefox') {
            steps {
                wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', autoDisplayName: true, debug: true, displayNameOffset: 0, installationName: 'Xvfb', parallelBuild: true, screen: '1024x758x24', timeout: 25]) {
                    sh 'mvn test -P integration-tests,firefox-testing'
                }
                sh 'rm ./de.dashup.model/src/main/resources/de/dashup/model/db/config/database.conf'
             }
        }
        stage('Publish test results') {
            when {
                branch 'master'
            }
            steps {
                sh 'bash service/publish-test-report.sh'
            }
        }
        stage('Analyze Project') {
            steps {
                sh 'bash service/publish-to-sonar.sh'
            }
        }
        stage('Deploy') {
            when {
                branch 'deployment'
            }
            steps {
                sh 'JENKINS_NODE_COOKIE=dontKillMe nohup bash service/restart-application.sh'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/TEST-*.xml'
            step( [ $class: 'JacocoPublisher' ] )
        }
    }
}