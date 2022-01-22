package com.parsley.v2

import java.sql.DriverManager
import scala.annotation.meta.{beanGetter, field, getter}

object Main {

    def main(args: Array[String]): Unit = {
        val a = student(1, 7.7).transcation().query().foreach(println)
        a.transcation().insert()
    }
}

case class student(age: Int,miaomiaomiao:Double)