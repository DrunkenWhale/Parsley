package com.parsley.v2

import com.parsley.v2.annotation.Text

import java.sql.DriverManager

object Main {
//    def main(args: Array[String]): Unit = {
//        Class.forName("com.mysql.cj.jdbc.Driver")
//        val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","3777777")
//        val resultSet = connection.prepareStatement("select * from author;").executeQuery()
//        while (resultSet.next()){
//            println(resultSet)
//        }
//    }

    def main(args: Array[String]): Unit = {
        val a = student(1,"q",1.1,2.2,true,'a',1L)
//        student(1,"q").productIterator
        a.query()
    }
}

case class student(age:Int,name:String,q:Float,w:Double,c:Boolean,l:Char,k:Long)