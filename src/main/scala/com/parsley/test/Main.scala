package com.parsley.test

import com.parsley.schema.{Column, PrimaryKey, ClassToSchema}
import com.parsley.ConvertClassToSchema
import com.parsley.schema.chars.CharType

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        println(ConvertClassToSchema.create(classOf[Test]))
    }
}

@ClassToSchema
class Test {

    @Column
    @CharType
    val name: String = ""

    @Column
    @CharType(size = 255)
    @PrimaryKey
    val age: Float = 1.0
}


