package com.parsley.orm.curd.query

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.Table
import com.parsley.orm.util.Util

import scala.reflect.ClassTag

object QueryManyToManyRelationImpl {

  def queryManyToManyImpl[T <: Product, F <: Product](table: Table[T], x: T)(implicit clsTag: ClassTag[F], tag: ClassTag[T]): List[F] = {
    val relationTable = table.manyToManyTables(clsTag.runtimeClass)
    val manyToManyRelationTableName = Util.getRelationTableName(table.name, relationTable.name)
    val sql = s"SELECT * FROM ${relationTable.name} " +
        s"WHERE ${table.primaryKeyName} IN " +
        s"(SELECT DISTINCT ${relationTable.name} " +
        s"FROM ${manyToManyRelationTableName} " +
        s"WHERE ${table.name}='${Util.findFieldValueFromClassByName(x, table.primaryKeyName)}');"

    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/

    ExecuteSQL.executeQuerySQL[F](sql, relationTable.columnType)

  }

}
