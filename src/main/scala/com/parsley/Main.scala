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

class Studenta(val name: String, var age: Int, val gender: Boolean) {
//    val exist: Boolean = true
}

object Main {
    def main(args: Array[String]): Unit = {

        DataBaseManager.register(MysqlConnection(database = "parsely", password = "3777777"))


        val students = table[Studenta]
        on(students)(student => declare(
            student.age is primaryKey,
            student.name is unique,
            student.gender is indexed
        ))
        students.create()
        students.insert(new Studenta("114514", 1919810, true))
    }
}