pipeline {
    agent any

    tools {
        jdk 'JDK-23'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Gradle Wrapper') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }

    post {

        always {
            junit 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true
        }

        success {
            echo 'Build and tests completed successfully.'
        }

        failure {
            echo 'Build or tests failed.'
        }
    }
}
