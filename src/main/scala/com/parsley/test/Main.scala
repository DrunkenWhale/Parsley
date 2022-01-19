package com.parsley.test

import com.parsley.schema.{Column, PrimaryKey, Table}
import com.parsley.ConvertClassToSchema
import com.parsley.schema.chars.CharType

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        println(ConvertClassToSchema.create(classOf[Test]))
    }
}

@Table
class Test {

    @Column
    @CharType
    @PrimaryKey
    val name: String = ""

    @Column
    @CharType(size = 255)
    @PrimaryKey
    val age: Float = 1.0

}

