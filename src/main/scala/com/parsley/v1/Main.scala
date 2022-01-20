package com.parsley.v1

import com.parsley.v1.schema.chars.CharType
import com.parsley.v1.schema.{Column, PrimaryKey, Schema}

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        println(ConvertClassToSchema.create(classOf[Test]))
    }
}

@Schema
class Test {

    @Column
    @CharType
    val name: String = ""

    @Column
    @CharType(size = 255)
    @PrimaryKey
    val age: Float = 1.0
}


