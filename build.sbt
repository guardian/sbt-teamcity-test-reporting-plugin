name := "sbt-teamcity-test-reporting-plugin"

organization := "com.gu"

sbtPlugin := true

releaseSettings

publishTo := {
  val isSnapshot = version.value.contains("-SNAPSHOT")
  val scalasbt = "http://scalasbt.artifactoryonline.com/scalasbt/"
  val (name, url) = if (isSnapshot)
    ("sbt-plugin-snapshots", scalasbt+"sbt-plugin-snapshots")
  else
    ("sbt-plugin-releases", scalasbt+"sbt-plugin-releases")
  Some(Resolver.url(name, new URL(url))(Resolver.ivyStylePatterns))
}

publishMavenStyle := false


