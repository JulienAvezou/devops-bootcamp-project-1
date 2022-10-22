
def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                    sh 'mvn package'
                }
            }
        }
        stage("build Docker image...") {
            steps {
                script {
                    gv.buildImage()
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER'])
                        sh 'docker build -t julienavezou/my-repo:jma-1.1 .'
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh 'docker push julienavezou/my-repo:jma-1.1'
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}
