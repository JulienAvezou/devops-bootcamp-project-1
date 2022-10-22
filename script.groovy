def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
} 

def buildImage() {
    echo 'building the Docker image...'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t julienavezou/my-repo:jma-1.1 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push julienavezou/my-repo:jma-1.1'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
