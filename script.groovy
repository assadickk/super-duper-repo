def buildImage() {
    return docker.build("${env.IMAGE_NAME}", "-f ${env.DOCKERFILE_NAME} ${env.BUILD_CONTEXT}")
}

def pushImage(dockerImage) {
    docker.withRegistry('', "${DOCKERHUB_ID}") {
        dockerImage.push()
        dockerImage.push("latest")
    }
}

def removeImages(imageName, imageNameLatest) {
    sh "docker rmi ${imageName} ${imageNameLatest}"
}

return this
