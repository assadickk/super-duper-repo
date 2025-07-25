pipeline {
    agent any

    environment {
        DOCKERHUB_ID = 'dockerhub'
        GITHUB_ID = 'jenkins_git'
        IMAGE_TAG = "v$BUILD_NUMBER"
        IMAGE_NAME_LATEST = "${env.IMAGE_BASE}:latest"
        WORK_DIR = '/home/ubuntu/'
        REMOTE_HOST = '56.228.11.61'
    }

    stages {
        stage('Build and push nginx image') {
            environment {
                IMAGE_BASE = 'asadkrmv/jenkins-nginx'
                DOCKERFILE_NAME = "./nginx/Dockerfile"
                BUILD_CONTEXT = "./nginx"
                IMAGE_NAME = "${env.IMAGE_BASE}:${env.IMAGE_TAG}"
            }
            
            steps {
                script {
                    def dockerImage = docker.build(env.IMAGE_NAME, "-f ${env.DOCKERFILE_NAME} ${env.BUILD_CONTEXT}")
                    docker.withRegistry('', env.DOCKERHUB_ID) {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }

        stage('Build and push logs image') {
            environment {
                IMAGE_BASE = 'asadkrmv/jenkins-logs'
                DOCKERFILE_NAME = "./logs/Dockerfile"
                BUILD_CONTEXT = "./logs"
                IMAGE_NAME = "${env.IMAGE_BASE}:${env.IMAGE_TAG}"
            }
            
            steps {
                script {
                    def dockerImage = docker.build(env.IMAGE_NAME, "-f ${env.DOCKERFILE_NAME} ${env.BUILD_CONTEXT}")
                    docker.withRegistry('', env.DOCKERHUB_ID) {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }
    }     

    post {
        success {
            withCredentials([sshUserPrivateKey(credentialsId: 'ub1_ssh', keyFileVariable: 'SSH_KEY', usernameVariable: 'SSH_USER')]) {
                sshCommand remote: [
                    host: "${env.REMOTE_HOST}",
                    user: 'ubuntu',
                    name: "${SSH_USER}",
                    identityFile: "${SSH_KEY}",
                    allowAnyHosts: true
                ],
                command: "cd ${env.WORK_DIR} && docker compose pull && docker compose up -d"
            }
        }
    }
}
