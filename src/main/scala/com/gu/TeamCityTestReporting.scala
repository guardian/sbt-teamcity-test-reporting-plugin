package com.gu

import sbt._
import org.scalatools.testing.{Event => TEvent, Result => TResult}

import Keys._
import java.io.{PrintWriter, StringWriter}

object TeamCityTestReporting extends Plugin {
  override def settings = Seq(
    testListeners ++= TeamCityTestListener.ifRunningUnderTeamCity
  )
}

class TeamCityTestListener extends TestReportListener {
  /** called for each class or equivalent grouping */
  def startGroup(name: String) {
    // we can't report to teamcity that a test group has started here,
    // because even if parallel test execution is disabled there may be multiple
    // projects running tests from different projects at the same time.
    // So if you tell TC that a test group has started, the tests from
    // different projects will get mixed up.
  }

  def nicelyFormatException(t: Throwable) = {
    val w = new StringWriter
    val p = new PrintWriter(w)
    t.printStackTrace(p)
    w.toString
  }

  /** called for each test method or equivalent */
  def testEvent(event: TestEvent) {
    for (e: TEvent <- event.detail) {

      // this is a lie: the test has already been executed and started by this point,
      // but sbt doesn't send an event when test starts
      teamcityReport("testStarted", "name" -> e.testName)

      e.result match {
        case TResult.Success => // nothing extra to report
        case TResult.Error | TResult.Failure =>
          teamcityReport("testFailed",
            "name" -> e.testName,
            "details" -> nicelyFormatException(e.error())
          )
        case TResult.Skipped =>
          teamcityReport("testIgnored", "name" -> e.testName)
      }

      teamcityReport("testFinished", "name" -> e.testName)

    }
  }


  /** called if there was an error during test */
  def endGroup(name: String, t: Throwable) { }
  /** called if test completed */
  def endGroup(name: String, result: TestResult.Value) { }


  // http://confluence.jetbrains.net/display/TCD65/Build+Script+Interaction+with+TeamCity
  def tidy(s: String) = s
    .replace("|", "||")
    .replace("'", "|'")
    .replace("\n", "|n")
    .replace("\r", "|r")
    .replace("\u0085", "|x")
    .replace("\u2028", "|l")
    .replace("\u2029", "|p")
    .replace("[", "|[")
    .replace("]", "|]")

  private def teamcityReport(messageName: String, attributes: (String, String)*) {
    println("##teamcity[" + messageName + " " + attributes.map {
      case (k, v) => k + "='" + tidy(v) + "'"
    }.mkString(" ") + "]")
  }
}

object TeamCityTestListener {
  // teamcity se
  private lazy val teamCityProjectName = Option(System.getenv("TEAMCITY_PROJECT_NAME"))
  lazy val ifRunningUnderTeamCity = teamCityProjectName.map(ignore => new TeamCityTestListener).toSeq
}



