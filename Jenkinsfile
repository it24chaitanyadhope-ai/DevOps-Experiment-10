pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Start Application') {
            steps {
                bat 'start "CampusFind" /B java -cp target/classes com.devops.App'
                bat 'timeout /t 5 /nobreak > nul'
            }
        }

        stage('Selenium Test') {
            steps {
                bat 'mvn compile exec:java "-Dexec.mainClass=com.devops.SeleniumTest"'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t chaitanyaa15/campusfind:latest .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    bat 'echo %DOCKER_PASSWORD% | docker login -u "%DOCKER_USERNAME%" --password-stdin'
                    bat 'docker push chaitanyaa15/campusfind:latest'
                }
            }
        }
    }
}