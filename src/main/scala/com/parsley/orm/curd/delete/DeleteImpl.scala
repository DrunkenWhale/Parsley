package com.parsley.orm.curd.delete

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Condition, Table}

object DeleteImpl {
  def deleteImpl(table: Table[_], condition: Condition): Unit = {
    val sql = s"DELETE FROM `${table.name}` $condition;"
    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)

    /*-------------------------------------*/
    ExecuteSQL.executeSQL(sql)
  }
}
