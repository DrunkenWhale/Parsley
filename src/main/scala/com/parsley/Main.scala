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


class Student(val name: String, val gender: Boolean, var age: Int) {}
class People(val name: String, val gender: Boolean, var age: Int) {
    val exist: Boolean = true
}

object Main {
    def main(args: Array[String]): Unit = {

        DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))

        val students = table[Student]
        students.query()
        on(students)(student => declare(
            student.age is primaryKey,
            student.name is unique,
            student.gender is indexed,
            student.name is indexed
        ))
        val students = table[People]
        students.create()
//        students.query().foreach(println)
//        on(students)(student => declare(
//            student.age is primaryKey,
//            student.name is unique,
//            student.gender is indexed,
//            student.name is indexed
//        ))
//        students.create()
        students.insert(new People("1145114", true, 1919810))
        students.query()
    }
}