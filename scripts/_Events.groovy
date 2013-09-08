includeTargets << grailsScript("_GrailsCompile")

def tryToLoadCounter = 0

loadKarmaTestTypeClass = {->
  if (tryToLoadCounter++ > 9) return

  def doLoad = {->
    classLoader.loadClass('de.is24.grails.tools.KarmaTestType')
  }
  try {
    doLoad()
  } catch (ClassNotFoundException ignore) {
    compilePlugins()
    loadKarmaTestTypeClass()
  }
}

loadKarmaTestType = {
  if (!binding.variables.containsKey("unitTests")) return
  def specTestTypeClass = loadKarmaTestTypeClass()

  [unit: unitTests, integration: integrationTests, functional : functionalTests].each { name, types ->
    if (!types.any { it.class == specTestTypeClass }) {
      types << specTestTypeClass.newInstance('javascript', name)
    }
  }
}

eventAllTestsStart = {
  loadKarmaTestType()
}

eventPackagePluginsEnd = {
  loadKarmaTestType()
}
