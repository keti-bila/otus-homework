pipeline {
    agent any
    tools {
        maven 'maven 3.6.0'
    }

    triggers {
        githubPush()
    }

    environment {
        LC_ALL = 'en_US.UTF-8'
        LANG    = 'en_US.UTF-8'
        LANGUAGE = 'en_US.UTF-8'
        EMAIL_TO = 'stadnik.katerina@gmail.com'
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/keti-bila/otus-homework.git', description: 'The target git url')
        string(name: 'GIT_BRANCH', defaultValue: 'allure-homework', description: 'The target git branch')
        choice(name: 'BROWSER_NAME', choices: ['chrome'], description: 'Pick the target browser in Selenoid')
        choice(name: 'BROWSER_VERSION', choices: ['91.0', '90.0'], description: 'Pick the target browser version in Selenoid')
    }

    stages {
        stage('Pull from GitHub') {
            steps {
                slackSend(message: "Notification from Jenkins Pipeline")
                git ([
                    url: "${params.GIT_URL}",
                    branch: "${params.GIT_BRANCH}"
                    ])
            }
        }
        stage('Run maven clean test') {
            environment {
                OTUS_CREDENTIALS = credentials('otus-test-creds')
            }
            steps {
		        // sh меняем на bat, если операционная система Windows
                bat "mvn clean test -Dpassword=%OTUS_CREDENTIALS_PSW% -Dbrowser=%BROWSER_NAME% -Demail=%OTUS_CREDENTIALS_USR%"
            }
        }
        stage('Backup and Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/', fingerprint: true
            }
            post {
                always {
                  script {

                    // Формирование отчета
                    allure([
                      includeProperties: false,
                      jdk: '',
                      properties: [],
                      reportBuildPolicy: 'ALWAYS',
                      results: [[path: 'target/allure-results']]
                    ])
                    println('allure report created')

                    // Достаем информацию по тестам из junit репорта
                    def summary = junit testResults: '**/target/surefire-reports/junitreports/*.xml'
                    println("summary generated")

                    def emailMessage = "${currentBuild.currentResult}: Job '${env.JOB_NAME}', Build ${env.BUILD_NUMBER}, Branch ${params.GIT_BRANCH}. \nPassed time: ${currentBuild.durationString}. \n\nTESTS:\nTotal = ${summary.totalCount},\nFailures = ${summary.failCount},\nSkipped = ${summary.skipCount},\nPassed = ${summary.passCount} \n\nMore info at: ${env.BUILD_URL}"

                    emailext (
                        subject: "Jenkins Report",
                        body: emailMessage,
                        to: "${EMAIL_TO}",
                        from: "jenkins@code-maven.com"
                        )

                    def colorCode = '#FF0000'
                    def slackMessage = "${currentBuild.currentResult}: Job '${env.JOB_NAME}', Build ${env.BUILD_NUMBER}. \nTotal = ${summary.totalCount}, Failures = ${summary.failCount}, Skipped = ${summary.skipCount}, Passed = ${summary.passCount} \nMore info at: ${env.BUILD_URL}"

                    slackSend(color: colorCode, message: slackMessage)
                  }
                }
            }
        }
    }
}