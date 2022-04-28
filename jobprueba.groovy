job('Job Actividad Docker') {
    description('Aplicacion para actividad de docker de hoy')
    scm {
        git('https://github.com/juanmazonflo/jobdocker', 'main') { node ->
            node / gitConfigName('juanmazonflo')
            node / gitConfigEmail('juanitocapotillo@gmail.com')
        }
    }
    triggers {
        scm('H/6 * * * *')
    }
    wrappers {
        nodejs('nodejs')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('juanmaflo/dockerprueba')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('docker-hub')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
