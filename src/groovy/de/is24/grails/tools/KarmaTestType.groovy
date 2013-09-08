package de.is24.grails.tools

import de.is24.util.karmatestrunner.junit.KarmaTestSuiteRunner
import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.event.GrailsTestRunNotifier
import org.codehaus.groovy.grails.test.junit4.listener.SuiteRunListener
import org.codehaus.groovy.grails.test.junit4.result.JUnit4ResultGrailsTestTypeResultAdapter
import org.codehaus.groovy.grails.test.report.junit.JUnitReportsFactory
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport
import org.junit.runner.Result
import org.junit.runner.RunWith
import org.junit.runners.Suite

import java.lang.reflect.Modifier

/**
 * Karma test type enables the JUnit @RunWith(KarmaTestSuiteRunner.class) annotation
 * when test file ends with 'KarmaSuite'.
 *
 * Integrates the JUnit test runner and grails.
 */
class KarmaTestType extends GrailsTestTypeSupport {

  static final SUFFIXES = ['KarmaSuite'].asImmutable()

  private Suite suite

  KarmaTestType(String name, String relativeSourcePath) {
    super(name, relativeSourcePath)
  }

  @Override
  protected List<String> getTestSuffixes() { SUFFIXES }

  @Override
  protected int doPrepare() {
    def testClasses = collectTestClasses()
    if (testClasses) {
      suite = createSuite(testClasses)
      suite.testCount()
    } else {
      0
    }
  }

  @Override
  protected GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
    def notifier = createNotifier(eventPublisher)
    def result = new Result()
    notifier.addListener(result.createListener())
    suite.run(notifier)

    notifier.fireTestRunFinished(result)
    new JUnit4ResultGrailsTestTypeResultAdapter(result)
  }

  private collectTestClasses() {
    def classes = []
    eachSourceFile { testTargetPattern, sourceFile ->
      Class testClass = sourceFileToClass(sourceFile)
      if (!Modifier.isAbstract(testClass.modifiers) && isRunByJUnitKarmaTestRunner(testClass)) {
        classes << testClass
      }
    }
    classes
  }

  private static Suite createSuite(classes) {
    new Suite(new JsRunnerBuilder(), classes as Class[])
  }

  private static boolean isRunByJUnitKarmaTestRunner(Class testClass) {
    (testClass.getAnnotation(RunWith) as RunWith)?.value() == KarmaTestSuiteRunner
  }

  private JUnitReportsFactory createJUnitReportsFactory() {
    JUnitReportsFactory.createFromBuildBinding(buildBinding)
  }

  private createListener(GrailsTestEventPublisher eventPublisher) {
    new SuiteRunListener(eventPublisher, createJUnitReportsFactory(), createSystemOutAndErrSwapper())
  }

  private createNotifier(eventPublisher) {
    def notifier = new GrailsTestRunNotifier(suite.testCount())
    notifier.addListener(createListener(eventPublisher))
    notifier
  }
}