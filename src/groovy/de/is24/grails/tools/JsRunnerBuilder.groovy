package de.is24.grails.tools

import de.is24.util.karmatestrunner.junit.KarmaTestSuiteRunner
import org.junit.runner.Runner
import org.junit.runners.model.RunnerBuilder

class JsRunnerBuilder extends RunnerBuilder {
  @Override
  Runner runnerForClass(Class<?> aClass) {
    return new KarmaTestSuiteRunner(aClass)
  }
}
