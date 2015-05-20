# Grails plugin karma test runner

This is a grails plugin to run javascript tests with [karma] in grails test-app lifecycle.
[karma]:http://karma-runner.github.io/

[karma] is an uprising javascript testing framework with support for multiple browser testing a lot more...
[karma]:http://karma-runner.github.io/

## Introduction

The plugin provides a new test type `javascript` which can be executed in the unit or functional test phase:
* to run all javascript unit test you can execute `test-app unit:javascript`
* to run all javascript e2e test you can execute `test-app functional:javascript`
* to run all unit tests inclusive javascript tests you can execute `test-app unit:`  as already known (same thing for all functional tests or all test with `test-app`)

To javascript tests can be in any format [karma|http://karma-runner.github.io/] provides.
To trigger the plugin to execute your tests you have create a normal test class (either in `test/unit` or `test/functional`) which has the name suffix `KarmaSuite`:
```java
@RunWith(KarmaTestSuiteRunner.class)
@KarmaTestSuiteRunner.KarmaConfigPath("./path/to/karma.conf.js")
public class JavaScriptUnitTestKarmaSuite {
}
```

For further information checkout and run "grails doc" to see the documentation.
Note: When this plugin is published to grailsCentral the documentation will be there...

A complete grails application which uses this plugin as a reference can be found at [Grails Angular Phonecat project](https://github.com/FlorianGrundig/grails-angular-phonecat)

##IMPORTANT
* make sure that you have installed a karma-remote-reporter version v0.2.x

##History

v0.2.4
* uses junit-karma-testrunner v.1.7 which doesn't include JUnit in the shaded jar anymore

v0.2.3
* uses junit-karma-testrunner v.1.6 which communicates with karma-remote-reporter via raw tcp sockets instead of using jetty + websockets

v0.2.1
* uses junit-karma-testrunner v.1.4 which supports Java 1.6 instead of only Java 1.7 and higher 

v0.2.1
* uses junit-karma-testrunner v.1.3

v0.2.0
* uses junit-karma-testrunner v.1.1
* more configuration options for running the tests in a developement and CI-Server environment
* causes a failing test suite when javascript syntax errors where reported
* Majorchange: The annotation KarmaProcessBuilderArgs is not supported anymore, use KarmaProcessName or KarmaStartupScripts instead



##Prerequisites

The plugin runs only on java 6 or higher versions.


In order to make the plugin run your javascript tests it will start [karma] with your
config provided by the test annotation `@KarmaTestSuiteRunner.KarmaConfigPath` (see above).
[karma]:http://karma-runner.github.io/

The plugin does not contain [nodejs] or [karma].
You have to install these frameworks by yourself.
[karma]:http://karma-runner.github.io/
[nodejs]:http://nodejs.org/



### Dependencies

#### karma-test-runner
Add the plugin as test dependency in your `BuildConfig.groovy`
```groovy
plugins {
   ...
   test ':karma-test-runner:0.2.1'
}
```


#### Nodejs
Make sure you have nodejs installed (see karma documentation for
compatible nodejs versions - currently versions 0.8.x and 0.10.x).

#### Karma

There is a fast way of setting up your grails application to test the plugin. Doing so
all required dependencies will be installed locally in a folder "node_modules" in your app root dir.
Do not commit the "node_modules" directory in your VCS.
* This is not suitable for CI environments like build-agents, which make clean checkouts.

The plugin provides a script "create-karma-package-json" which you can run in grails console of your grails app.

This script will create a file "package.json" in your app root dir. With this file you can use
```sh
npm install --save-dev
```
 to install karma and karma-remote-reporter locally.


If you want to do it not the fast way then read further otherwise you can skip this section.


The nodejs application karma in version 0.10.x needs to be installed.
A global installation is recommended when using the plugin in CI environments (e.g. the grails app will be tested by a jenkins-agent):
```sh
npm install -g karma
```
For testing the plugin a local installation is good enough (annotate your test class with something like @KarmaTestSuiteRunner.KarmaProcessName("./karma_test_project/node_modules/karma/bin/karma") )
```sh
npm install karma
```

##### karma-remote-reporter
The [karma-remote-reporter](https://github.com/ImmobilienScout24/karma-remote-reporter) is a karma plugin which reports the test results
to a remote server. The remote server is started by the [KarmaTestSuiteRunner](https://github.com/ImmobilienScout24/junit-karma-testrunner)
which is used by this plugin.

To install the karma-remote-reporter you can do it globally:
```sh
npm install -g karma-remote-reporter
```
or locally:
```sh
npm install karma-remote-reporter
```

## Usage

* Please make sure you have all required dependencies installed.

To use the plugin to test your javascript tests you have to keep in mind that this plugin will only report the
test results in a JUnit way and provides a triggering test type. This plugin *does not* execute the tests itself
which is done by karma (http://karma-runner.github.io/).

So there two ways you have to setup your application to execute javascript tests:
* setup a karma config to define which tests should be executed and how
* setup the karma test runner to run karma with a given karma config

### Setup a karma config
To setup a karma config means to create a config file which karma uses when executing the tests.
Look at karma (http://karma-runner.github.io) configuration homepage for the format of a configuration file.


!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!  A complete grails application which uses this plugin as a reference can be found here: (https://github.com/FlorianGrundig/grails-angular-phonecat).
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



In this reference app you'll find two karma config files
* one for executing the unit tests
** @grails-angular-phonecat/test/javascript/config/karma.conf.js@
* and one for executing the e2e tests
** @grails-angular-phonecat/test/javascript/config/karma-e2e.conf.js@

#### Unit Tests
A minimum karma config file for running unit tests (`karma.conf.js`):
```js
module.exports = function (config) {
    config.set({

        basePath: './../../../',
        files: [
            'web-app/js/*.js',
            'test/javascript/unit/**/*.js'
        ],

        browsers: [ 'Chrome'],
        reporters: ['remote'],
        frameworks: ["jasmine"],
        autoWatch: false,
        singleRun: true,
        remoteReporter: {
            host: 'localhost',
            port: '9889'
        },
        plugins: [
            'karma-jasmine',
            'karma-chrome-launcher',
            'karma-remote-reporter'
        ]
    });
};
```

* basePath: must point to your app root dir
* files: must include all files needed by your javascript tests (ant style)
* browsers: list of browsers to run the tests with (must be locally installed)
* reporters: list of reporters - the 'remote' reporter must be used so that this plugin can receive the test results
* frameworks: frameworks you have used in your javascript tests
* autoWatch: must be false
* singleRun: must be true
* remoteReporter: must be matching to your setup of the karma test runner (use localhost and port 9889 if you have not configured otherwise)
* plugins: list with plugins - `karma-remote-reporter` must be included so that the 'remote' reporter is available

#### E2E tests (functional tests)
A minimum karma config file for e2e unit tests (`karma-e2e.conf.js`):
```js
module.exports = function (config) {
    config.set({

        basePath: './../../../',
        files: [
            'test/javascript/e2e/**/*.js'
        ],
        browsers: ['Chrome'],
        reporters: ['remote'],
        frameworks: ['ng-scenario'],
        autoWatch: false,
        singleRun: true,
        proxies: {
            '/': 'http://localhost:8080/angular-phonecat/',
            '/angular-phonecat': 'http://localhost:8080/angular-phonecat/',
            '/angular-phonecat/static': 'http://localhost:8080/angular-phonecat/static'
        },
        urlRoot: '/__karma/',
        remoteReporter: {
            host: 'localhost',
            port: '9889'
        },
        plugins: [
            'karma-jasmine',
            'karma-chrome-launcher',
            'karma-ng-scenario',
            'karma-remote-reporter'
        ]
    });
};
```

* basePath: must point to your app root dir
* files: must include all files needed by your javascript tests (ant style) - in general only your test file because this are e2e tests
* browsers: list of browsers to run the tests with (must be locally installed)
* reporters: list of reporters - the @'remote'@ reporter must be used so that this plugin can receive the test results
* frameworks: frameworks you have used in your javascript tests
* autoWatch: must be false
* singleRun: must be true
* proxies: if you want use relative paths in your tests like `browser.navigateTo("/")` you have to proxy them - if you use the grails resource plugin you have to proxy the static path!!!
* urlRoot: used by karma - to avoid path name clashes with your app choose a path which is not handled by your app
* remoteReporter: must be matching to your setup of the karma test runner (use localhost and port 9876 if you have not configured otherwise)
* plugins: list with plugins - `karma-remote-reporter` must be included so that the `remote` reporter is available

If you want to use karma for instant feedback (autoWatch: true, singleRun: false) make another karma config file (e.g. karma.manual.conf) and use karma without the plugin - you can (should) do both :)

In a CI environment you should use only browsers supported by your CI-Server. There're ways to copy a build-agent specific karma.conf when running the build...

Look at the [karma homepage](http://karma-runner.github.io/) - there're several more configuration options which might be interesting.
One more: if you have two config files one for development and one for the CI-Server -> extract the "files" and "exclude" section into a separate js-file to avoid duplicates...

### Setup karma test runner

To setup the karma test runner you should keep the following in mind. If you create a test class in the `/test/unit` folder you can only execute
javascript tests which do not require a running webapp (unit tests). The other way around: if your javascript tests require a running
webapp put your test class into the `/test/functional` folder.

Before you start the javascript tests the first time make sure that you have setup your karma config correctly.
To test your karma config start karma manually from the webapp root dir with:
```
karma start /path/to/your/karma.conf
```
If you're running e2e tests you have to start the webapp manually before you execute the e2e tests.

All test files which should be run by the KarmaTestSuiteRunner should end with `*KarmaSuite.groovy`.

Here's is sample test file for running karma e2e tests:
```java
package de.is24.demo.karma;

import de.is24.util.karmatestrunner.junit.KarmaTestSuiteRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(KarmaTestSuiteRunner.class)
@KarmaTestSuiteRunner.KarmaConfigPath("./src/test/resources/config/karma-e2e.conf.js")
public class JavaScriptE2eTestKarmaSuite {

    @BeforeClass
    public static void setup(){
        // setup your test scenario
    }

    @AfterClass
    public static void teardown(){
        // cleanup your test scenario
    }
}
```

The most important thing is to run JUnit with the KarmaTestSuiteRunner.
With the KarmaConfigPath you should choose the appropriate karma config file location.

With KarmaStartupScripts you can also point to a script/batch file which setup the path to find karma etc. Have a look at the junit-karma-testrunner documentation...

Use the `@BeforeClass` and `@AfterClass` annotated methods to setup/cleanup your test scenario which is only useful for e2e tests.
If you need different test scenarios feel free to make another test class with it's own karma conf.

Mostly all of the KarmaTestSuiteRunner annotations can be overwritten by system properties to run in CI-Server environments (see junit-karma-testrunner docu).

The plugin provides a new test type `javascript` which can be executed in the unit or functional test phase:
* to run all javascript unit test you can execute `test-app unit:javascript`
* to run all javascript e2e test you can execute `test-app functional:javascript`
* to run all unit tests inclusive javascript tests you can execute `test-app unit:`  as already known (same thing for all functional tests or all test with `test-app`)


A complete grails application which uses this plugin as a reference can be found [here](https://github.com/FlorianGrundig/grails-angular-phonecat).

####  Additional Setup for Windows and Mac environments

When you want to run the tests in a windows or mac environment, where might be some issues with finding the karma executable and the port the plugin receives the test results.
To get around this you can configure the KarmaTestSuiteRunner not to start karma directly but to start a script which will start karma by itself.
A script allows you to set the path environment variable for the karma executable or for the browser executables too.

To use a script look at this test class  example for a windows batch file:

```java
@RunWith(KarmaTestSuiteRunner.class)
@KarmaTestSuiteRunner.KarmaConfigPath("./src/test/resources/config/karma-e2e.conf.js")
@KarmaTestSuiteRunner.KarmaRemoteServerPort(9999)                        // set your port the default might not work
@KarmaTestSuiteRunner.KarmaStartupScripts("./scripts/e2e-test.bat")  // set the path to your script
public class JavaScriptE2eTestKarmaSuite {
    ...
}
```

In the script file you can modify the path for the karma executeable etc:
```bat
set PATH=%PATH%;"C:\Users\foo\appdata\npm"    # depends on your environment - search for the karma executable

set CHROME_BIN="path\to\your\chrome.exe"
set IE_BIN="path\to\your\iexplore.exe"


set BASE_DIR=%~dp0
karma start "%BASE_DIR%\..\test\javascript\config\karma.conf.js" %*        # path to your karma config
```

In the karma config file set the port you configured in your test class:
```js
 remoteReporter: {
            host: 'localhost',
            port: '9999'
        },
```







