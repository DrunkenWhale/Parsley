package com.parsley.dsl

import com.parsley.connect.DataBaseManager
import com.parsley.connect.DataBaseManager.statment
import com.parsley.dsl.ColumnAttribute.attributeMappingToSQL
import com.parsley.dsl.Table.createSQLString

import scala.collection.mutable
import scala.reflect.ClassTag

protected class Table[T](val tableName: String) {

    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    private val columnMap = mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]()

    private val indexedSeq = mutable.Seq[String]()

    def create(): Unit = {
        val str = createSQLString(this)
        println(str)
        DataBaseManager.statment().execute(str)
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

    // side effect method
    def addNewColumnMessage(table: Table[_])(key: String, value: Tuple2[String, Seq[ColumnAttribute]]): Unit = {
        table.columnMap.put(key, value)
    }

    def createSQLString(table: Table[_]): String = {
        val columnString = table.columnMap.toList.map(x =>
            val stringBuilder: mutable.StringBuilder = new StringBuilder()

            for (attribute <- x._2._2) {
                stringBuilder.append(attribute match {
                    case IndexAttribute() =>
                        table.indexedSeq.appended(x._1)
                        " "
                    case _ => " " + attributeMappingToSQL(attribute)
                })
            }
            "`" + x._1 + "` " + x._2._1 + stringBuilder.result() + ",\n"
        ).mkString

        val indexedString = "INDEX(" + table.indexedSeq.map(x=>x) + ")\n" +
            ");"

        s"CREATE TABLE IF NOT EXISTS `${table.tableName}`(\n" +
            s"$columnString"

    }
}
