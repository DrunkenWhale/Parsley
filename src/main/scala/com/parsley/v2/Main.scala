package com.parsley.v2

import java.sql.DriverManager
import scala.annotation.meta.{beanGetter, field, getter}

object Main {

    def main(args: Array[String]): Unit = {
        val a = student(1, Text("114514"), "sss")
        //        println(a.transcation().create(primaryKey = "age",uniqueColumns = List("age")))
        a.transcation().query()
    }
}

case class student(age: Int, text: Text, test: String)