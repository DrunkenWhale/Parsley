package com.parsley

import com.parsley.schema.PrimaryKey

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        ConvertClassToSchema.create(classOf[Test])
    }
}
class Test{
    @PrimaryKey
    val name:String = ""

    val age:Float = 1.0

}

