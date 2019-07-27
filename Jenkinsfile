pipeline {
    agent none

    stages {
        stage('Test') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    chmod +x ./gradlew
                    ./gradlew test
                '''
            }
        }
        stage('Build') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    sed -i 's/PARKINGSMART_PSW/${DB_PASSWORD}/g' src/main/resources/application-test.yml
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('Deploy Dev') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    chmod +x deploy.sh
                    sh deploy.sh
                '''
            }
        }

        stage('Approve of Deploy Prod') {
          steps {
            input message: 'deploy to Prod?'
          }
        }

        stage('Deploy Prod') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    echo deploy prod
                '''
            }
        }
    }
}