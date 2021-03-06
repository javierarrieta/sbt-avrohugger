import sbt._
import Keys._
import ScriptedPlugin._


object build extends Build {
    val sbtAvrohugger = Project(
        id = "sbt-avrohugger",
        base = file("."),
        settings = Defaults.defaultSettings ++ scriptedSettings ++ Seq[Project.Setting[_]](
            organization := "com.julianpeeters",
            version := "0.0.1",
            sbtPlugin := true,
            resolvers += Resolver.file("Local Ivy Repository", file("/home/julianpeeters/.ivy2/local/"))(Resolver.ivyStylePatterns),
            libraryDependencies ++= Seq(
                "com.julianpeeters" % "avrohugger-core_2.10" % "0.0.2",
                 "org.apache.avro" % "avro" % "1.7.4"),
            scalaVersion := "2.10.4",
            scalacOptions in Compile ++= Seq("-deprecation"),
            description := "Sbt plugin for compiling Avro sources",
            publishMavenStyle := true,
            publishArtifact in Test := false,
            publishTo <<= version { (v: String) =>
              val nexus = "https://oss.sonatype.org/"
              if (v.trim.endsWith("SNAPSHOT"))
                Some("snapshots" at nexus + "content/repositories/snapshots")
              else
                Some("releases" at nexus + "service/local/staging/deploy/maven2")
            },
            pomIncludeRepository := { _ => false },
            licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
            homepage := Some(url("https://github.com/julianpeeters/sbt-avrohugger")),
            pomExtra := (
              <scm>
                <url>git://github.com/julianpeeters/sbt-avrohugger.git</url>
                <connection>scm:git://github.com/julianpeeters/sbt-avrohugger.git</connection>
              </scm>
              <developers>
                <developer>
                  <id>julianpeeters</id>
                  <name>Julian Peeters</name>
                  <url>http://github.com/julianpeeters</url>
                </developer>
              </developers>)
        )
    )
}
