package com.parsley.orm.curd.insert

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Table, util}
import com.parsley.orm.util.Util

import scala.reflect.ClassTag

object InsertImpl {

  /* insert */
  def insertImpl[T <: Product](table: Table[T], x: T): Unit = {
    val elementNameValueSeq = Util.getNameValueSeqFromCaseClass(x)
    val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")
    val elementValueList = elementNameValueSeq.map((_, value) => value)
    val sql = s"INSERT INTO `${table.name}` ($elementNameListString) VALUES (${List.fill(elementNameValueSeq.length)("?").mkString(",")})"
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, elementValueList)

  }

}
