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
 stage('package Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn package'
}
}
}

  stage('Sonar stage'){
    steps{
    withSonarQubeEnv('SonarQube'){
     bat 'mvn clean package sonar:sonar' 
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
