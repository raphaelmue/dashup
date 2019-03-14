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
                sh 'mkdir ./de.dashup.model/src/main/resources/de/dashup/model/db'
                sh 'mkdir ./de.dashup.model/src/main/resources/de/dashup/model/db/config'
                sh 'cp /var/lib/jenkins/workspace/dashup_database.conf ./de.dashup.model/src/main/resources/de/dashup/model/db/config/database.conf'
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Testing') {
            steps {
                wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', autoDisplayName: true, debug: true, displayNameOffset: 0, installationName: 'Xvfb', parallelBuild: true, screen: '1024x758x24', timeout: 25]) {
                    sh 'mvn test'
                }
                sh 'rm ./de.dashup.model/src/main/resources/de/dashup/model/db/config/database.conf'
            }
        }
        stage('Deploy') {
            when {
                branch 'deployment'
            }
            steps {
                sh 'mvn spring-boot:run'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/TEST-*.xml'
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