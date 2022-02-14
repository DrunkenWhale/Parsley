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

class Schema {
    val name: String = "114"
    val number: Int = 514
    val age: Long = 114514
}

object Main {
    def main(args: Array[String]): Unit = {

        DataBaseManager.register(MysqlConnection(database = "parsely",password = "3777777"))

        val t = table[Schema]()
        on(t)(schema => declare(
            schema.number is primaryKey,
            schema.name is unique
        ))

        t.create()

    }
}