Provides reporting of test success and failure for tests run by
[simple build tool](https://github.com/harrah/xsbt)
in a format
that [TeamCity](http://www.jetbrains.com/teamcity) understands.

This uses the [simple mechanism](http://confluence.jetbrains.net/display/TCD65/Build+Script+Interaction+with+TeamCity)
built into TeamCity to provide information about build progress, combined with the test listener
mechanism in sbt.

To use, add the following line to a plugins.sbt file in your project directory:

```
addSbtPlugin("com.gu" % "sbt-teamcity-test-reporting-plugin" % "1.5")
```

It will do nothing at all when not running under TeamCity, but
when it _is_ running under TeamCity (detected by the presence of the `TEAMCITY_PROJECT_NAME` environment variable)
it will report success and failure of executed tests.


SBT Versions
------------

These versions are all fairly closely equivalent, just updated to cope with changes in SBT's test interface:

* **SBT v0.12** - use plugin **v1.3**
* **SBT v0.13** - use plugin **v1.5**

The plugin is published to the main SBT plugin repository, so no further configuration should be necessary. 


Known Limitations
=================

Sbt only reports that a test has been run when it completes. So this plugin has to
tell TeamCity at that point that the test has both started and finished: this means
TeamCity thinks that all your tests run *really* fast! Do not be misled...
