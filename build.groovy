pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr:'5' , artifactNumToKeepStr: '5'))
    timestamps()
    }
  stages {
    stage('CheckOut') {
      steps {
        echo 'Checking out project from Bitbucket....'
        cleanWs()
        git(url: 'git@github.com:vamsi8977/nodejs.git', branch: 'main')
      }
    }
    stage('Build') {
      steps {
        script {
          sh '''
            npm install
            npm audit fix --force
            npm run build
            npm test
          '''
        }
      }
    }
  }
  post {
    success {
      echo "The build passed."
    }
    failure {
      echo "The build failed."
    }
    cleanup {
      deleteDir()
    }
  }
}
