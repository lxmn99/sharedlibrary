def call(String repoUrl) {
 pipeline{
agent any
stages{
stage('Compile Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn clean compile'
}
}
}
stage('Test Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn test'
}
}
}
stage('Build Stage'){
steps{
withMaven(maven: 'Maven3'){
bat 'mvn install'
}
}
}
}
}
}
