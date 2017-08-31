name := "scala_js_learning"

version := "0.1.0"

scalaVersion := "2.11.8"

enablePlugins(ScalaJSPlugin)

// This is an application with a main method
//scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)
