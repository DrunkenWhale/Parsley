package com.parsley.v2

import java.sql.DriverManager
import scala.annotation.meta.{beanGetter, field, getter}

object Main {

    def main(args: Array[String]): Unit = {
        val a = student(1,Text("114514"))
        //        student(1,"q").productIteratorobject Main {
        ////    def main(args: Array[String]): Unit = {
        ////        Class.forName("com.mysql.cj.jdbc.Driver")
        ////        val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","3777777")
        ////        val resultSet = connection.prepareStatement("select * from author;").executeQuery()
        ////        while (resultSet.next()){
        ////            println(resultSet)
        ////        }
        ////    }
        a.transcation().query()
    }
}

case class student(age: Int,text:Text)