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
            echo 'Build et tests terminés avec succès.'
        }

        failure {
            echo 'Le build ou les tests ont échoué.'
        }
    }
}