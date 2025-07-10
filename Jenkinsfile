def gv

pipeline {
    agent any
    environment {
        DOCKERHUB_ID = 'docker-creds'
        IMAGE_TAG = "v$BUILD_NUMBER"
        IMAGE_NAME = "${env.IMAGE_BASE}:${env.IMAGE_TAG}"
        IMAGE_NAME_LATEST = "${env.IMAGE_BASE}:latest"
    }

    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage('Clone repo') {
            steps {
                git branch: 'master',
                    credentialsId: 'jenkins_git',
                    url: 'git@github.com:assadickk/super-duper-repo.git'
            }
        }

        stage('Build and push nginx image') {
            environment {
                IMAGE_BASE = 'asadkrmv/jenkins-nginx'
                DOCKERFILE_NAME = "./nginx/Dockerfile"
                BUILD_CONTEXT = "./nginx"
            }

            def dockerImage
            
            steps {
                script {
                    dockerImage = gv.buildImage()
                    gv.pushImage(dockerImage)
                    gv.removeImages(env.IMAGE_NAME, env.IMAGE_NAME_LATEST)
                }
            }
        }

        stage('Build and push logs image') {
            environment {
                IMAGE_BASE = 'asadkrmv/jenkins-logs'
                DOCKERFILE_NAME = "./logs/Dockerfile"
                BUILD_CONTEXT = "./logs"
            }
            
            def dockerImage
            
            steps {
                script {
                    dockerImage = gv.buildImage()
                    gv.pushImage(dockerImage)
                    gv.removeImages(env.IMAGE_NAME, env.IMAGE_NAME_LATEST)                }
            }
        }

    }        
}