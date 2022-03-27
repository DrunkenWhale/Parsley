package com.parsley.orm.curd.query

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.util.Util
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
  
}
