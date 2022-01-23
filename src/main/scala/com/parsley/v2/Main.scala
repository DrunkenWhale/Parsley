package com.parsley.v2

import java.sql.DriverManager
import scala.annotation.meta.{beanGetter, field, getter}

object Main {

    def main(args: Array[String]): Unit = {

        val p = Person(114514,"野兽先辈",true)
        p.transcation().create("age")
        p.transcation().insert()
        p.transcation().query().foreach(println)

    }
}

case class Person(age:Int,name:String,gender:Boolean)