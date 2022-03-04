package com.parsley.orm

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.attribute.{AutoIncrement, Indexed, NotNull, PrimaryKey, Unique}

import scala.reflect.ClassTag

object DSL {

    /*------------------------------table----------------------------------------------*/

    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]

    def table[T <: Product](name: String)(implicit classTag: ClassTag[T]) = Table.apply[T](name)
    

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

    export com.parsley.orm.curd.UpdateImpl.into
    export com.parsley.orm.curd.UpdateImpl.update
    export com.parsley.orm.curd.UpdateImpl.where
    export UpdateOperation.==>

    /*-------------------------------delete---------------------------------------------*/

    export com.parsley.orm.curd.DeleteImpl.delete

    /*-----------------------------condition-------------------------------------------*/

    export com.parsley.orm.Condition.===
    export com.parsley.orm.Condition.limit
    export com.parsley.orm.Condition.`*`

    /*-----------------------------attribute---------------------------------------------*/

    val primaryKey = new PrimaryKey()
    val autoIncrement = new AutoIncrement()
    val notNull = new NotNull()
    val unique = new Unique()
    val indexed = new Indexed()
}
