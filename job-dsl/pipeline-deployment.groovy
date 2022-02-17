String jenkinsAgent = 'sag-devops-jenkins-slave'
String codeRepoCredentialsID = 'jenkinsusr-ldap'

pipeline {
    agent any
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages{
        // stage('Import Job DSL') {
        //     options { timeout (time: 1, unit: 'MINUTES') }
        //     steps {
        //         checkout scm: [$class: 'GitSCM', userRemoteConfigs: [[url: "https://gitdev.devops.krungthai.com/mfoa/devops/jenkins-configuration/pipeline.git", credentialsId: codeRepoCredentialsID ]], branches: [[name: 'release']]], poll: false
        //     }
        // }
        stage('Run Job DSL') {
            options { timeout (time: 5, unit: 'MINUTES') }
            steps {
                script {
                    sh 'ls'
                    def inputFile = readFile 'job-dsl/job-dsl-2'
                    jobDsl scriptText: inputFile         
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
