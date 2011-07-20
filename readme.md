Provides reporting of test success and failure for tests run by
[simple build tool](https://github.com/harrah/xsbt)
in a format
that [TeamCity](http://www.jetbrains.com/teamcity) understands.

This uses the [simple mechanism](http://confluence.jetbrains.net/display/TCD65/Build+Script+Interaction+with+TeamCity)
built into TeamCity to provide information about build progress, combined with the test listener
mechanism in sbt.

To use,

1. Work out what released version you want to use by looking at the tags list for this repository.

2. Add the plugin to your sbt build, by creating project/plugins/project/plugins.scala that looks like:

        import sbt._

        object Plugins extends Build {
          lazy val plugins = Project("plugins", file("."))
            .dependsOn(
              uri("git://github.com/guardian/sbt-teamcity-test-reporting-plugin.git#1.0")
            )
        }


It will do nothing at all when not running under TeamCity, but
when it is running under TeamCity (detected by the presence of the `TEAMCITY_PROJECT_NAME` environment variable)
it will report success and failure of executed test.

Known Limitations
=================

Sbt only reports that a test has been run when it completes. So this plugin has to
tell TeamCity at that point that the test has both started and finished: this means
TeamCity thinks that all your tests run *really* fast! Do not be misled...
