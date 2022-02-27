package com.parsley.orm

import sourcecode.Text

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

object DSL {

    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]

    def declare(columnNameWithAttribute: (String, Seq[Attribute])*): Seq[(String, Seq[Attribute])] = {
        columnNameWithAttribute
    }

    /**
     * side effect!
     * */
    def on[T <: Product](table: Table[T])
                        (operation: (T => Seq[(String, Seq[Attribute])]))
                        (implicit classTag: ClassTag[T]): Unit = {
        val nameWithAttributeSeq = operation(DataToInstance.fakeInstance[T])
        //change table's map
        Table.putAttribute(table)(nameWithAttributeSeq)
    }

    extension (self:
               sourcecode.Text[String]
                   | sourcecode.Text[Int]
                   | sourcecode.Text[Long]
                   | sourcecode.Text[Double]
                   | sourcecode.Text[Float]
                   | sourcecode.Text[Boolean]) {
        def is(attribute: Attribute*): (String, Seq[Attribute]) = {
            (self.source.split("\\.").last, attribute)
        }
    }


}
