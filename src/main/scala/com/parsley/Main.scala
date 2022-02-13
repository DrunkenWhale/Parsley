package com.parsley

import com.parsley.dsl.{ColumnBody, ColumnExpression, Table, declare, index, nullable, on, primaryKey, table, unique}
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
        val t = table[Schema]()
        on(t)(schema => declare(
            schema.number is (primaryKey,nullable),
            schema.name is unique,
            schema.age is index
        ))
        t.create()

    }
}
