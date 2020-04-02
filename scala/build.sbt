lazy val root = (project in file(".")).
  settings(
    name := "ws",
    version := "1.0",
    scalaVersion := "2.13.1",
    mainClass in assembly := Some("Main"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.31",
      "com.typesafe.akka" %% "akka-stream" % "2.5.31",
      "com.typesafe.akka" %% "akka-http-core" % "10.1.11"
    )
  )
