package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.Table

import scala.reflect.ClassTag

object InsertImpl {
    
    /* insert */
    def insert[T <: Product](x: T)(table: Table[T]): Unit = {
        val element = x.asInstanceOf[Tuple]
        val elementLength = element.productArity
        val elementNameValueSeq =
            for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))
        val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")
        val elementValueList: IndexedSeq[Any] = elementNameValueSeq.map((_, value) => value)
        val sql = s"INSERT INTO `${table.name}` ($elementNameListString) VALUES (${List.fill(elementLength)("?").mkString(",")})"
        // log
        ExecuteSQL.executeInsertSQL(sql, elementValueList)

    }
}
