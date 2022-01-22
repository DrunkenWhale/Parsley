package com.parsley.v2

import java.sql.DriverManager
import scala.annotation.meta.{beanGetter, field, getter}

object Main {

    def main(args: Array[String]): Unit = {
        val a = student(1, 7.7)
//        a.transcation().create("age")
        a.transcation().queryAll().foreach(println)
        //        println(a.transcation().create(primaryKey = "age",uniqueColumns = List("age")))
        println(a.transcation().queryAll())
    }
}

case class student(age: Int,miaomiaomiao:Double)