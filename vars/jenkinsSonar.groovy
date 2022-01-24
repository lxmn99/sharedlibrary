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
 stage('Compile Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn clean compile'
}
}
}
 
 stage('Testing Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn test'
}
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
              timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: false
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
