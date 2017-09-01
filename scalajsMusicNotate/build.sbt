name := "scalajsMusicNotate"

version := "0.1.0"

scalaVersion := "2.11.8"
enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

// Specify js library dependencies
skip in packageJSDependencies := false
jsDependencies ++= Seq(
  "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
  ProvidedJS / "abcjs_basic_3.0-min.js" dependsOn "jquery.js"
)
