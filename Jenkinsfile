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
                sh './gradlew clean build --enable-preview'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test --enable-preview'
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true
        }

        success {
            echo 'Build and  Test are terminated sucessfully.'
        }

        failure {
            echo 'Build or Test failed .'
        }
    }
}