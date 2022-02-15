package com.parsley

import com.parsley.connect.DataBaseManager
import com.parsley.connect.connection.MysqlConnection
import com.parsley.dsl.*
import com.parsley.util.fakeInstance
import sourcecode.{Line, Text}
import com.parsley.dsl.ImplicitConvert.*

import java.lang.reflect.Field
import scala.reflect.ClassTag
import scala.language.postfixOps
case class Student(name:String,age:Int,gender:Boolean)
object Main {
    def main(args: Array[String]): Unit = {

        DataBaseManager.register(MysqlConnection(database = "parsely",password = "3777777"))


        val students = table[Student]
        on(students)(student=>declare(
            student.name is primaryKey,
            student.age is (notnull,unique,indexed),
            student.gender is indexed
        ))
        students.create()
    }
}