lazy val root = (project in file(".")).
  settings(
    name := "ws",
    version := "1.0",
    scalaVersion := "2.11.8",
    mainClass in assembly := Some("Main"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-core" % "2.4.8"
    )
  )
