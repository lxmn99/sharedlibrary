def call(String repoUrl) {
  pipeline {
       agent any
       stages {
         
           stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
           stage("Cleaning workspace") {
               steps {
                 script{
                      withMaven(maven: 'Maven3'){
                      bat 'mvn clean compile'
                        }
                  }
               }
           }
           stage("Running Testcase") {
              steps {
                   sh "mvn test"
               }
           }
           stage("Packing Application") {
               steps {
                   sh "mvn package -DskipTests"
               }
           }
       }
   }
}
