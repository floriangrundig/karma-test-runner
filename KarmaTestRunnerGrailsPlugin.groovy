class KarmaTestRunnerGrailsPlugin {
    def version = "0.2.1"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "src/docs/**/*",
        "src/samples/**/*",
    ]

    def title = "Karma Test Runner Plugin"
    def author = "Florian Grundig"
    def authorEmail = "florian.grundig@gmx.de"
    def description = 'Runs javascript unit or e2e tests with karma. Provides a test type \"javascript\" which can be run in any test phase.'
    def documentation = "https://github.com/ImmobilienScout24/karma-test-runner"

    def license = "APACHE"
    def organization = [ name: "ImmobilienScout24", url: "http://www.immobilienscout24.de" ]
    def issueManagement = [ system: "Github", url: "https://github.com/ImmobilienScout24/karma-test-runner/issues" ]
    def scm = [ url: "https://github.com/ImmobilienScout24/karma-test-runner" ]
}
