package com.parsley.orm.curd.insert

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.Table
import com.parsley.orm.util.Util

import scala.reflect.ClassTag

object InsertRelationImpl {

  def insertRelationImpl[T <: Product, F <: Product](table: Table[T], x: T, element: F)(implicit classTag: ClassTag[F]): Unit = {
    val tb = table.followedTables(classTag.runtimeClass).asInstanceOf[Table[F]]

    val relationColumnValue = Util.findFieldValueFromClassByName(x, table.primaryKeyName)

    val elementNameValueSeq = Util.getNameValueSeqFromCaseClass(x)

    val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")

    val elementValueList = elementNameValueSeq.map((_, value) => value)

    val columnRelation = s"`${table.name}_${tb.name}`"

    val sql = s"INSERT INTO `${tb.name}` ($elementNameListString,$columnRelation) " +
        s"VALUES (${List.fill(elementNameValueSeq.length + 1)("?").mkString(",")})"
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, elementValueList.appended(relationColumnValue))

  }


}
