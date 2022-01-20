def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven 'MAVEN_HOME'
           jdk 'JAVA_HOME'
       }
       stages {
         stage("Tools initialization") {
               steps {
                 withMaven(maven: 'MAVEN_HOME'){
                   bat 'mvn --version'
                   bat 'java -version'
               }
         }
         }
           stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
           stage("Cleaning workspace") {
               steps {
                 script{
                      withMaven(maven: 'MAVEN_HOME'){
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
