grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
        mavenRepo "https://oss.sonatype.org/content/repositories/snapshots"
	}

	dependencies {
		compile 'de.is24.util:junit-karma-testrunner:1.7-SNAPSHOT'
	}

	plugins {
		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
