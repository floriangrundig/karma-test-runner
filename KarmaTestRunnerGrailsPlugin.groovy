class KarmaTestRunnerGrailsPlugin {
    // the plugin version
    def version = "0.1-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Karma Test Runner Plugin" // Headline display name of the plugin
    def author = "Florian Grundig "
    def authorEmail = "florian.grundig@gmx.de"
    def description = '''\
Plugin to run javascript unit or e2e tests with karma. The plugin provides a test type "javascript" which can be run in any test phase.
For example "test-app unit:javascript" will execute the javascript unit tests - "test-app functional:javascript"
will execute the javascript e2e tests. "test-app" will execute all tests (including the javascript tests).
The new test type will be triggered if any test file with the name suffix "SuiteJS" will be found in test/**/*"
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/ImmobilienScout24/karma-test-runner"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Immobilienscout24", url: "http://www.immobilienscout24.de" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "GITHUB", url: "https://github.com/ImmobilienScout24/karma-test-runner/issues" ]

    // Online location of the plugin's browseable source code.
   def scm = [ url: "https://github.com/ImmobilienScout24/karma-test-runner" ]

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}
