name := "Parsley"

version := "0.8.1"

scalaVersion := "3.1.1"

libraryDependencies ++= Seq(
    "mysql" % "mysql-connector-java" % "8.0.25",
    "com.lihaoyi" %% "sourcecode" % "0.2.8"
)

lazy val Parsley = (project in file("."))
    .settings(
        name := "Parsley"
    )