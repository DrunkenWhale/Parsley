package com.parsley

import sourcecode.Line

import java.lang.reflect.Field
import scala.reflect.ClassTag

class Schema {
    val name = "野兽先辈"
    val age = 114514
}

trait te {
    outer =>
    protected def name = "???"
}

object Main {
    def main(args: Array[String]): Unit = {
        val schema = new Schema()

        val c: (Schema => Unit) = (s => println(sourcecode.Text(s.age).source))
        class t(x: Int, y: String) {}
        val construct = (classOf[t].getConstructors.head)
        test1[t]
        //
        //        def getName[T](xs: sourcecode.Text[T])(implicit name: sourcecode.Name) = (xs.source, xs.value)
        //
        //        println(getName(schema.age))
    }

    def test1[T](implicit Clazz:ClassTag[T]): Unit = {
        println(Clazz.runtimeClass.getDeclaredConstructors.head.getParameterTypes.foreach(x=>println(x.getSimpleName)))
    }

    def test[T](xs: String)(implicit ClassTagT: ClassTag[T]): Unit = {
        val clazz = ClassTagT.runtimeClass
        
        clazz.getFields foreach println

    }
}
