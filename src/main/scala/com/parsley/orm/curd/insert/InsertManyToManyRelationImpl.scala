package com.parsley.orm.curd.insert

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Table, util}
import com.parsley.orm.util.Util

import scala.reflect.ClassTag

object InsertManyToManyRelationImpl {
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
}
