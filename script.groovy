def buildImage(imageName, dockerfileName, buildContext) {
    return docker.build(imageName, "-f ${dockerfileName} ${buildContext}")
}

def pushImage(dockerImage, dockerhubId) {
    docker.withRegistry('', dockerhubId) {
        dockerImage.push()
        dockerImage.push("latest")
    }
}

def removeImages(imageName, imageNameLatest) {
    sh "docker rmi ${imageName} ${imageNameLatest} || true"
}

return this
