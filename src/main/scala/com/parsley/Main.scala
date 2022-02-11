package com.parsley

//import com.parsley.dsl.MethodIsClass

import com.parsley.dsl.*
import com.parsley.dsl.Operation.declare
import dsl.convert.AddIsMethod
import dsl.convert.AddNamedMethod

class Schema{
    val name="野兽先辈"
    val age = 114514
}

object Main {
    def main(args: Array[String]): Unit = {
        test()
    }

    def test(): Unit ={
        on[Schema](schema=>declare(
            schema.name named "下北泽" is PrimaryKey
        ))
        println(SchemaColumn.convertSchemaColumnClassToSQL(new Schema().name named "下北泽" is PrimaryKey))
    }
}
