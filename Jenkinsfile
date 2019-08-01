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
                    sh 'nohup java -jar -Dspring.profiles.active=test build/libs/parkingsmart-backend-1.0-SNAPSHOT.jar > log.txt 2>&1 &'
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