package com.parsley.test

import com.parsley.schema.{PrimaryKey, Table}
import com.parsley.ConvertClassToSchema
import com.parsley.schema.`type`.chars.CharType

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        new Test
        ConvertClassToSchema.create(classOf[Test])
    }
}

@Table
class Test{

    @PrimaryKey
    val name:String = ""

    @CharType(size = 255)
    @PrimaryKey
    val age:Float = 1.0

}

