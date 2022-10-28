#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
    remote: 'https://github.com/JulienAvezou/devops-bootcamp-project-4.git',
    credentialsId: 'github-credentials'
    ]
)

def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.6'
    }
    stages {
         stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                    echo "Executing pipeline for $BRANCH_NAME"
                }
            }
        }
        stage("increment version") {
            steps {
                script {
                    echo "incrementing app version..."
                    sh 'mvn build-helper:parse-version \
                        versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage("build jar") {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                   buildJar()
                }
            }
        }
        stage("build Docker image...") {
              when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    buildImage "julienavezou/my-repo:${IMAGE_NAME}"
                    dockerLogin()
                    dockerPush "julienavezou/my-repo:${IMAGE_NAME}"
                }
            }
        }
        stage("deploy") {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    sshagent(['ec2-server-key']) {
                        def dockerCmd = 'docker run -p 3000:8080 -d julienavezou/my-repo:jma-3.0'
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@18.193.46.11 ${dockerCmd}"
                    }
                }
            }
        }
    }   
}
