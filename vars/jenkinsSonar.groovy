def call(String repoUrl) {
 pipeline{
agent any
stages{
 stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
 
  stage('Code analysis stage'){
    steps{
    withSonarQubeEnv('SonarQube'){
     bat 'mvn clean package sonar:sonar' 
    }
    }
    
  }
 
 stage("Quality Gate") {
            steps {
              timeout(time: 5, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
          }
 
stage('Build artifact Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn install'
}
}
}
}
}
}
