#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
    remote: 'https://github.com/JulienAvezou/devops-bootcamp-project-1.git',
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
                    buildImage 'julienavezou/my-repo:jma-3.0'
                    dockerLogin()
                    dockerPush 'julienavezou/my-repo:jma-3.0'
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
                    gv.deployApp()
                }
            }
        }
    }   
}
