package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Condition, Table}

object DeleteImpl {
    def delete(condition: Condition)(table: Table[_]): Unit = {
        val sql = s"DELETE FROM `${table.name}` $condition;"
        /*-----------------Logger--------------*/

        Logger.logginSQL(sql)

        /*-------------------------------------*/
        ExecuteSQL.executeSQL(sql)
    }
}
