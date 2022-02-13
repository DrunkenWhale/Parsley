package com.parsley.dsl

import com.parsley.connect.DataBaseManager.statment

import scala.collection.mutable
import scala.reflect.ClassTag

protected class Table[T](val tableName: String) {
    
    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    val columnMap = mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]()

    def create(): Unit = {
        val sql:String =
            s"CREATE TABLE IF NOT EXIST(" +
                s"" +
                s"" +
                s"" +
                s")"
    }
    
    def query(): Unit = {

    }

    def insert(x: T): Unit = {
        val sql: String = s" INSERT INTO  "
        //        statment().execute()
    }

    def update(): Unit = {

    }

    def delete: Unit = {

    }
    
}

object Table {


}
