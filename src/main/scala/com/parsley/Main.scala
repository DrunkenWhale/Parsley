package com.parsley

import com.parsley.dsl.{ColumnBody, ColumnExpression, primaryKey}
import com.parsley.util.getSampleInstance
import sourcecode.{Line, Text}
import com.parsley.dsl.ImplicitConvert.*

import java.lang.reflect.Field
import scala.reflect.ClassTag
import sourcecode.Text.generate

import scala.language.postfixOps
import com.parsley.dsl.Operation.declare

class Schema(val i: Int, val c: String, val x: Boolean) {
    val name = "野兽先辈"
    val age = 114514
}

trait te {
    outer =>
    protected def name = "???"
}

object Main {
    def main(args: Array[String]): Unit = {
        val schema = new Schema(1, "dsf", true)

        //        implicit def p[T](x: T): Text[Int] = {
        //            Text[Int](1)
        //        }
        //        class MyValue(val name:Int){
        //            def are(x:String) ={
        //                println(x)
        //                this
        //            }
        //        }
        //
        //        implicit def po(x:Int): MyValue ={
        //            new MyValue(x)
        //        }

//        implicit class pop[T](x: T) {
//            def is(s: String): Unit = {
//                println(s)
//            }
//        }
//
//        def y[T](xs: sourcecode.Text[T]*) = {
//            xs.foreach(x =>
//                println(x.source.split(" ").head.split("\\.").tail.head)
//            )
//        }
//
//        y(schema.age is "114514", schema.name is "asdad", schema.x is "???")

        declare(
            schema.name is (primaryKey),
            schema.age.is(primaryKey)
        )


        //
        //        def getName[T](xs: sourcecode.Text[T])(implicit name: sourcecode.Name) = (xs.source, xs.value)
        //
        //        println(getName(schema.age))
    }

    def test1[T](implicit Clazz: ClassTag[T]): Unit = {
        println(Clazz.runtimeClass.getDeclaredConstructors.head.getParameterTypes.foreach(x => println(x.getSimpleName)))
    }

    def test[T](xs: String)(implicit ClassTagT: ClassTag[T]): T = {
        val clazz = ClassTagT.runtimeClass
        getSampleInstance[T]

    }
}
