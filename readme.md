Provides reporting of test success and failure for tests run by
[simple build tool](https://github.com/harrah/xsbt)
in a format
that [TeamCity](http://www.jetbrains.com/teamcity) understands.

This uses the [simple mechanism](http://confluence.jetbrains.net/display/TCD65/Build+Script+Interaction+with+TeamCity)
built into TeamCity to provide information about build progress, combined with the test listener
mechanism in sbt.

To use, add the following line to a plugins.sbt file in your project directory:

```
addSbtPlugin("com.gu" % "sbt-teamcity-test-reporting-plugin" % "1.4")
```

It's published to the main SBT plugin repository, so no further configuration should be necessary. Note that due to a 
change in SBT's test interface 1.3 is the latest version published for the 0.12 series of SBT.

It will do nothing at all when not running under TeamCity, but
when it is running under TeamCity (detected by the presence of the `TEAMCITY_PROJECT_NAME` environment variable)
it will report success and failure of executed test.

Known Limitations
=================

Sbt only reports that a test has been run when it completes. So this plugin has to
tell TeamCity at that point that the test has both started and finished: this means
TeamCity thinks that all your tests run *really* fast! Do not be misled...
