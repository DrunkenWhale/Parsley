package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.{Condition, Table, UpdateOperation}

object UpdateImpl {
    
    def update(updateOperation: UpdateOperation): UpdateOperation = {
        updateOperation
    }

    extension (self: Condition | UpdateOperation) {
        def into(table: Table[_]): Unit = {
            val sql = s"UPDATE `${table.name}` $self;"
            println(sql)
            ExecuteSQL.executeUpdateSQL(sql)
        }
    }

    extension (self: UpdateOperation) {
        def where(condition: Condition = Condition.`*`): Condition = {
            val res = new Condition{
                override def toString: String = {
                    this.sqlString.append(s"${self} ${condition.sqlString.result()}").result()
                }
            }
            res
        }
    }
    
}
