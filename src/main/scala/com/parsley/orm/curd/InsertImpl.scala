package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.Table

import scala.reflect.ClassTag

object InsertImpl {


    // unuseful method
    // for the interest
    // for example:
    // you can use insert(?)(table) or insert(?) in table
    // they have the same result
    extension[T <: Product] (self: Table[T] => _) {
        def in(table: Table[T])(implicit classTag: ClassTag[T]): Unit = {
            self(table)
        }
    }
    
    /* insert */
    def insert[T <: Product](x: T)(table: Table[T]): Unit = {
        val element = x.asInstanceOf[Tuple]
        val elementLength = element.productArity
        val elementNameValueSeq =
            for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))
        val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")
        val elementValueList: IndexedSeq[Any] = elementNameValueSeq.map((_, value) => value)
        val sql = s"INSERT INTO `${table.name}` ($elementNameListString) VALUES (${List.fill(elementLength)("?").mkString(",")})"
        /*-----------------Logger--------------*/

        Logger.logginSQL(sql)

        /*-------------------------------------*/
        ExecuteSQL.executeInsertSQL(sql, elementValueList)

    }
}
