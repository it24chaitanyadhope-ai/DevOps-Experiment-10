pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Selenium Test') {
            steps {
                bat 'mvn compile exec:java "-Dexec.mainClass=com.devops.SeleniumTest"'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t campusfind:latest .'
            }
        }
    }
}