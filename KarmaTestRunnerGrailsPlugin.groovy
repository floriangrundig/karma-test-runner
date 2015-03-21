class KarmaTestRunnerGrailsPlugin {
    def version = "0.2.4-SNAPSHOT"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "src/docs/**/*",
        "src/samples/**/*",
    ]

    def title = "Karma Test Runner Plugin"
    def author = "Florian Grundig"
    def authorEmail = "florian.grundig@gmx.de"
    def description = 'Runs javascript unit or e2e tests with karma. Provides a test type \"javascript\" which can be run in any test phase.'
    def documentation = "https://github.com/FlorianGrundig/karma-test-runner"

    def license = "APACHE"
    def issueManagement = [ system: "Github", url: "https://github.com/FlorianGrundig/karma-test-runner/issues" ]
    def scm = [ url: "https://github.com/FlorianGrundig/karma-test-runner" ]
}
