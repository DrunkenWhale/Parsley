package com.parsley

import com.parsley.dsl.{ColumnBody, ColumnExpression, on, primaryKey, unique}
import com.parsley.util.fakeInstance
import sourcecode.{Line, Text}
import com.parsley.dsl.ImplicitConvert.*

import java.lang.reflect.Field
import scala.reflect.ClassTag
import scala.language.postfixOps
import com.parsley.dsl.Operation.declare

class Schema {
    val name: String = "114"
    val number: Int = 514
}

object Main {
    def main(args: Array[String]): Unit = {
        on[Schema](schema => declare(
            schema.number is primaryKey,
            schema.name is unique
        ))
    }
}
