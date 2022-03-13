package com.parsley.orm.curd.query

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.curd.util.CRUDUtil
import com.parsley.orm.{Condition, Table}

import scala.reflect.ClassTag

/**
 * method implement:
 * Table.query
 *
 * */
object QueryImpl {

  def queryImpl[T <: Product](table: Table[_], condition: Condition)(implicit classTag: ClassTag[T]): List[T] = {

    val columnNameString = table.columnName.map(x => "`" + x + "`").mkString(",")

    val sql = s"SELECT $columnNameString FROM `${table.name}` ${condition}"

    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/

    ExecuteSQL.executeQuerySQL[T](sql, table.columnType)

  }

  def queryManyToManyImpl[T <: Product, F <: Product](table: Table[T], x: T)(implicit clsTag: ClassTag[F], tag: ClassTag[T]): List[F] = {
    val relationTable = table.manyToManyTables(clsTag.runtimeClass)
    val manyToManyRelationTableName = CRUDUtil.getRelationTableName(table.name, relationTable.name)
    val sql = s"SELECT * FROM ${relationTable.name} " +
        s"WHERE ${table.primaryKeyName} IN " +
        s"(SELECT DISTINCT ${relationTable.name} " +
        s"FROM ${manyToManyRelationTableName} " +
        s"WHERE ${table.name}='${CRUDUtil.findFieldValueFromClassByName(x, table.primaryKeyName)}');"

    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/

    ExecuteSQL.executeQuerySQL[F](sql, relationTable.columnType)

  }

  def queryRelationImpl[T <: Product, F <: Product](table: Table[T], x: T)(implicit clsTag: ClassTag[F]): List[F] = {
    val tb = table.followedTables(clsTag.runtimeClass)
    val value: Any = CRUDUtil.findFieldValueFromClassByName(x, table.primaryKeyName)
    val followedTableJoinColumnName = s"${table.name}_${tb.name}"
    val columnNameString = tb.columnName.map(x => s"`${tb.name}`.`$x`").mkString(",")
    val sql =
      s"SELECT $columnNameString" +
          s" FROM `${tb.name}`" +
          s" JOIN `${table.name}`" +
          s" ON `${tb.name}`.`$followedTableJoinColumnName`=`${table.name}`.`${table.primaryKeyName}`" +
          s" WHERE `${table.name}`.`${table.primaryKeyName}`='$value';"
    Logger.logginSQL(sql)
    ExecuteSQL.executeQuerySQL[F](sql, tb.columnType)
  }

}
