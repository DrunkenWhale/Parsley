package com.parsley.orm

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.attribute.{AutoIncrement, Indexed, NotNull, PrimaryKey, Unique}

import scala.reflect.ClassTag

object DSL {

    /*------------------------------table----------------------------------------------*/

    extension (mainTable: Table[_]) {
        def <==(followTable: Table[_]) = {
            followTable.followingTables.put(mainTable.clazz, mainTable)
            mainTable.followedTables.put(followTable.clazz, followTable)
        }
    }

    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]

    def table[T <: Product](name: String)(implicit classTag: ClassTag[T]) = Table.apply[T](name)


    /*-------------------------------create------------------------------------------*/
    
    export com.parsley.orm.curd.create.CreateImpl.is
    export com.parsley.orm.curd.create.CreateImpl.on
    export com.parsley.orm.curd.create.CreateImpl.declare

    /*-------------------------------insert-------------------------------------------*/

//    export com.parsley.orm.curd.insert.InsertImpl.insert

    /*-------------------------------query--------------------------------------------*/

//    export com.parsley.orm.curd.QueryImpl.query
//    export com.parsley.orm.curd.QueryImpl.from

    /*-------------------------------update---------------------------------------------*/

    export com.parsley.orm.curd.update.UpdateImpl.into
    export com.parsley.orm.curd.update.UpdateImpl.update
    export com.parsley.orm.curd.update.UpdateImpl.where
    export UpdateOperation.==>

    /*-------------------------------delete---------------------------------------------*/

    export com.parsley.orm.curd.delete.DeleteImpl.delete

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
