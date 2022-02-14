package com.parsley.dsl

import com.parsley.connect.DataBaseManager.statment

import scala.collection.mutable
import scala.reflect.ClassTag

protected class Table[T](val tableName: String) {

    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    private val columnMap = mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]()

    def create(): String = {
        println(columnMap.toList)
        val sql: String =
            s"CREATE TABLE IF NOT EXISTS $tableName (" +
                s"" +
                s"" +
                s"" +
                s")"

        sql
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

protected object Table {

    def addNewColumnMessage(table: Table[_])(key: String, value: Tuple2[String, Seq[ColumnAttribute]]): Unit = {
        table.columnMap.put(key, value)
    }

}
