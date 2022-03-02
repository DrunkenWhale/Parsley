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

    def update[T <: Product]()(): Unit = {

    }

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
