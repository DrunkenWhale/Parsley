package com.parsley.orm.curd.insert

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.Table
import com.parsley.orm.curd.util
import com.parsley.orm.curd.util.CRUDUtil

import scala.reflect.ClassTag

object InsertImpl {

  /* insert */
  def insertImpl[T <: Product](table: Table[T], x: T): Unit = {
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

  def relatedManyToManyImpl[T <: Product, F <: Product](table: Table[T], x: T, element: F)(implicit classTag: ClassTag[F]): Unit = {
    val relationTable = table.manyToManyTables(classTag.runtimeClass) // will throw NullPointerException
    val relationTableName = CRUDUtil.getRelationTableName(relationTable.name, table.name)
    val primaryKeyName1 = table.primaryKeyName
    val primaryKeyName2 = relationTable.primaryKeyName
    val sql = s"INSERT INTO `${relationTableName}` (`$primaryKeyName1`,`$primaryKeyName2`) VALUES (?, ?)"
    val primaryKeyValue1 = util.CRUDUtil.findFieldValueFromClassByName(element, table.primaryKeyName)
    val primaryKeyValue2 = util.CRUDUtil.findFieldValueFromClassByName(element, relationTable.primaryKeyName)
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, Seq(primaryKeyValue1, primaryKeyValue2))
  }

  def insertRelationImpl[T <: Product, F <: Product](table: Table[T], x: T, element: F)(implicit classTag: ClassTag[F]): Unit = {
    val tb = table.followedTables(classTag.runtimeClass).asInstanceOf[Table[F]]

    val relationColumnValue = CRUDUtil.findFieldValueFromClassByName(x, table.primaryKeyName)

    val elementLength = element.productArity

    val elementNameValueSeq =
      for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))

    val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")

    val elementValueList: IndexedSeq[Any] = elementNameValueSeq.map((_, value) => value)

    val columnRelation = s"`${table.name}_${tb.name}`"

    val sql = s"INSERT INTO `${tb.name}` ($elementNameListString,$columnRelation) " +
        s"VALUES (${List.fill(elementLength + 1)("?").mkString(",")})"
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeInsertSQL(sql, elementValueList.appended(relationColumnValue))

  }


}
