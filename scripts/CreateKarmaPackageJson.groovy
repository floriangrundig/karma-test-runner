includeTargets << grailsScript("_GrailsInit")

target(createKarmaPackageJson: "Creates a package.json in the root dir for easy installing the required dependencies with 'npm install' (quickstart - local installation)") {
  ant.copy(
      file: "${karmaTestRunnerPluginDir}/src/samples/package.json",
      todir: "${basedir}/",
      overwrite: false)

  println '''
  karma-test-runner quickstart
  ----------------------------
  A file "package.json" was created in your applications root directory (if there was already a "package.json" it's not overwritten).
  With this file you can either install the required nodejs dependencies (karma and karma-remote-reporter) locally for a
  quickstart testing with "npm install" or you can install them globally (e.g. for a jenkins-agent) with "npm -g install".
  If "npm" is unknown you have to install nodejs first.
'''
}

setDefaultTarget(createKarmaPackageJson)
