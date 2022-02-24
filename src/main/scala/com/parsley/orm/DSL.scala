package com.parsley.orm

import scala.reflect.ClassTag

object DSL {

    def table[T]()(implicit clazzTag: ClassTag[T]) = Table[T]()

    def table[T](name: String)(implicit clazzTag: ClassTag[T]) = Table[T](name)


    /* ---------------create------------- */

    def on[T](table: Table[T])(columnExpressions: ColumnExpression*): Unit = {

    }

    def declare(): Unit = {

    }


    extension (self: ColumnMeta) {
        def ==>(attributes: Attribute*): Unit = {
            ColumnExpression(self.name,"",attributes)
        }
    }
}
