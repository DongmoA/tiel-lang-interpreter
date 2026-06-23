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
                // Print the actual JDK version Jenkins is using (should be 23).
                sh 'java -version'
                // Do NOT pass --enable-preview here: it is not a Gradle option.
                // The flag is handled in build.gradle.kts.
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
            // allowEmptyResults avoids the "No test report files were found"
            // error when the build fails before tests run.
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/reports/tests/test/**', allowEmptyArchive: true
        }

        success {
            echo 'Build and Test terminated successfully.'
        }

        failure {
            echo 'Build or Test failed.'
        }
    }
}