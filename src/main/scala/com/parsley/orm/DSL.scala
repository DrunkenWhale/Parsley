package com.parsley.orm

import scala.reflect.ClassTag

object DSL {

    /*-------------------------------create------------------------------------------*/

    export com.parsley.orm.curd.CreateImpl.create
    export com.parsley.orm.curd.CreateImpl.is
    export com.parsley.orm.curd.CreateImpl.on
    export com.parsley.orm.curd.CreateImpl.declare

    /*-------------------------------insert-------------------------------------------*/

    export com.parsley.orm.curd.InsertImpl.insert
    export com.parsley.orm.curd.InsertImpl.in

    /*-------------------------------query--------------------------------------------*/

    export com.parsley.orm.curd.QueryImpl.query
    export com.parsley.orm.curd.QueryImpl.from

    /*-----------------------------condition-------------------------------------------*/

    export com.parsley.orm.Condition.===
    export com.parsley.orm.Condition.limit

    /*-----------------------------attribute---------------------------------------------*/

    export com.parsley.orm.Attribute.PrimaryKey
    export com.parsley.orm.Attribute.Indexed
    export com.parsley.orm.Attribute.AutoIncrement
    export com.parsley.orm.Attribute.Unique
    export com.parsley.orm.Attribute.NotNull

    /*------------------------------table----------------------------------------------*/
    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]

}
