package com.parsley.orm

import com.parsley.connect.execute.ExecuteSQL

import scala.reflect.ClassTag

object DSL {

    /*------------------------------table----------------------------------------------*/

    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]


    // unuseful method
    // for the interest
    // for example:
    // you can use insert(?)(table) or insert(?) in table
    // they have the same result
    extension[T <: Product] (self: Table[T] => _) {
        def in(table: Table[T])(implicit classTag: ClassTag[T]): Unit = {
            self(table)
        }
    }


    /*-------------------------------create------------------------------------------*/

    export com.parsley.orm.curd.CreateImpl.create
    export com.parsley.orm.curd.CreateImpl.is
    export com.parsley.orm.curd.CreateImpl.on
    export com.parsley.orm.curd.CreateImpl.declare

    /*-------------------------------insert-------------------------------------------*/

    export com.parsley.orm.curd.InsertImpl.insert

    /*-------------------------------query--------------------------------------------*/

    export com.parsley.orm.curd.QueryImpl.query
    export com.parsley.orm.curd.QueryImpl.from

    /*-------------------------------update---------------------------------------------*/

    def update(updateOperation: UpdateOperation): UpdateOperation = {
        updateOperation
    }

    extension (self: Condition) {
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


    export UpdateOperation.==>

    /*-------------------------------delete---------------------------------------------*/

    export com.parsley.orm.curd.DeleteImpl.delete

    /*-----------------------------condition-------------------------------------------*/

    export com.parsley.orm.Condition.===
    export com.parsley.orm.Condition.limit
    export com.parsley.orm.Condition.`*`

    /*-----------------------------attribute---------------------------------------------*/

    export com.parsley.orm.Attribute.PrimaryKey
    export com.parsley.orm.Attribute.Indexed
    export com.parsley.orm.Attribute.AutoIncrement
    export com.parsley.orm.Attribute.Unique
    export com.parsley.orm.Attribute.NotNull

}
