package com.parsley

import scala.annotation.compileTimeOnly

object Main {
    def main(args: Array[String]): Unit = {
        ConvertClassToSchema.create(classOf[Test])
    }
}
@AnTest(name = "")
class Test{
    @AnTest(name = "")
    val name:String = ""

    val age:Float = 1.0

}

