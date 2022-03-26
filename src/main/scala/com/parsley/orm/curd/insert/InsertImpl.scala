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

  def relatedManyToManyImpl[T <: Product, F <: Product](table: Table[T], x: T, element: F)(implicit classTag: ClassTag[F]): Unit = {
    val relationTable = table.manyToManyTables(classTag.runtimeClass) // will throw NullPointerException
    val relationTableName = Util.getRelationTableName(relationTable.name, table.name)
    val sql = s"INSERT INTO `${relationTableName}` (`${table.name}`,`${relationTable.name}`) VALUES (?, ?)"
    val primaryKeyName1 = table.primaryKeyName
    val primaryKeyName2 = relationTable.primaryKeyName
    val primaryKeyValue1 = util.Util.findFieldValueFromClassByName(x, table.primaryKeyName)
    val primaryKeyValue2 = util.Util.findFieldValueFromClassByName(element, relationTable.primaryKeyName)
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, Seq(primaryKeyValue1, primaryKeyValue2))
  }

  def insertRelationImpl[T <: Product, F <: Product](table: Table[T], x: T, element: F)(implicit classTag: ClassTag[F]): Unit = {
    val tb = table.followedTables(classTag.runtimeClass).asInstanceOf[Table[F]]

    val relationColumnValue = Util.findFieldValueFromClassByName(x, table.primaryKeyName)

    val elementNameValueSeq = Util.getNameValueSeqFromCaseClass(x)

    val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")

    val elementValueList= elementNameValueSeq.map((_, value) => value)

    val columnRelation = s"`${table.name}_${tb.name}`"

    val sql = s"INSERT INTO `${tb.name}` ($elementNameListString,$columnRelation) " +
        s"VALUES (${List.fill(elementNameValueSeq.length + 1)("?").mkString(",")})"
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, elementValueList.appended(relationColumnValue))

  }


}
