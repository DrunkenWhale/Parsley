name := "Parsley"

version := "0.1"

scalaVersion := "3.0.2"
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies ++= Seq(
    "mysql" % "mysql-connector-java" % "8.0.25",
    "com.lihaoyi" %% "sourcecode" % "0.2.7"
)