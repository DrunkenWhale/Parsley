package com.parsley

import com.parsley.util.getEmptyInstance
import sourcecode.Line

import java.lang.reflect.Field
import scala.reflect.ClassTag

class Schema (val i:Int,val c:String,val x:Boolean){
    val name = "野兽先辈"
    val age = 114514
}

trait te {
    outer =>
    protected def name = "???"
}

object Main {
    def main(args: Array[String]): Unit = {
        val schema = new Schema(1,"dsf",true)

        val c: (Schema => Unit) = (s => println(sourcecode.Text(s.age).source))
        class t(x: Int, y: String) {}
        val construct = (classOf[t].getConstructors.head)
        println(test[Schema](schema.name).x)
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
        getEmptyInstance[T]

    }
}
