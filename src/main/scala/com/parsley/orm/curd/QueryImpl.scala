package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Condition, Table}

import scala.reflect.ClassTag

object QueryImpl {
    /* query */
    def query(condition: Condition) = {
        condition
    }

    extension (condition: Condition) {
        def from[T <: Product](table: Table[T])(implicit classTag: ClassTag[T]): List[T] = {
            val columnNameString = table.columnName.map(x => "`" + x + "`").mkString(",")
            val sql = s"SELECT $columnNameString FROM `${table.name}` ${condition}"
            /*-----------------Logger--------------*/

            Logger.logginSQL(sql)

            /*-------------------------------------*/
            ExecuteSQL.executeQuerySQL[T](sql, table.columnType)
        }
    }
}