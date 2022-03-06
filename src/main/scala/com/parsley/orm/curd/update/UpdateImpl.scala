package com.parsley.orm.curd.update

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Condition, Table}

object UpdateImpl {


    def updateImpl(table: Table[_], updateOperation: UpdateOperation, condition: Condition): Unit = {
        val conditionSQL = new Condition {
            override def toString: String = {
                this.sqlString.append(s"${updateOperation} ${condition.sqlString.result()}").result()
            }
        }
        val sql = s"UPDATE `${table.name}` $conditionSQL;"
        /*-----------------Logger--------------*/

        Logger.logginSQL(sql)

        /*-------------------------------------*/
        ExecuteSQL.executeUpdateSQL(sql)
    }

}
