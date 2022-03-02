package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.{Condition, Table}

object DeleteImpl {
    def delete[T <: Product](condition: Condition=Condition())(table: Table[_]): Unit = {
        val sql = s"DELETE FROM `${table.name}` $condition;"
        println(sql)
        ExecuteSQL.executeSQL(sql)
    }
}
