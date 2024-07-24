pipeline {
  agent {
    node { label 'android' }
  }

  stages {
    stage('Lint & Unit Test') {
      parallel {
        stage('checkStyle') {
          steps {
            // We use checkstyle gradle plugin to perform this
            sh './gradlew checkStyle'
          }
        }

        stage('Unit test data module') {
          steps {
            // Execute your Unit Test
            sh './gradlew data:test'
          }
        }
        stage('Unit test domain module') {
          steps {
            // Execute your Unit Test
            sh './gradlew domain:test'
          }
        }
        stage('Unit test presentation module') {
          steps {
            // Execute your Unit Test
            sh './gradlew presentation:test'
          }
        }
      }
    }

    /*stage('UI Testing') {
      steps {
        script {
          if (currentBuild.result == null
              || currentBuild.result == 'SUCCESS') {
          // Start your emulator, testing tools
          sh 'emulator @Nexus_Emulator_API_24'
          sh 'appium &'

          // You're set to go, now execute your UI test
          sh 'rspec spec -fd'
          }
        }
      }
    }

    stage('Deploy') {
      steps {
        script {
          if (currentBuild.result == null
              || currentBuild.result == 'SUCCESS') {
             if(env.BRANCH_NAME ==~ /master/) {
               // Deploy when the committed branch is master (we use fastlane to complete this)
               sh 'fastlane app_deploy'
          }
        }
      }
    }*/
  }

  post {
    always {
      archiveArtifacts(allowEmptyArchive: true, artifacts: 'app/build/outputs/apk/production/release/*.apk')

      // And kill the emulator?
      sh 'adb emu kill'
    }
  }

}