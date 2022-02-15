package com.parsley.dsl

import com.parsley.connect.DataBaseManager
import com.parsley.connect.DataBaseManager.statment
import com.parsley.dsl.ColumnAttribute.attributeMappingToSQL
import com.parsley.dsl.Table.createSQLString

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

protected class Table[T](val tableName: String) {

    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    private val columnMap = mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]()

    private val indexColumnList = mutable.ListBuffer[String]()

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
    // change table columnMap
    def addNewColumnMessage(table: Table[_])(key: String, value: Tuple2[String, Seq[ColumnAttribute]]): Unit = {
        table.columnMap.put(key, value)
    }


    val createSQLString = (table: Table[_]) => {
        s"CREATE TABLE IF NOT EXISTS `${table.tableName}`(\n" +
            s"${columnString(table)}" +
            s"${indexString(table.indexColumnList)}" + "\n"
            + ");"

    }

    // side effect
    // table's index list will be changed
    // indexColumnList will refactor to immutable in future version
    val columnString: Table[_] => String = (table: Table[_]) => {
        table.columnMap.toList.map(x =>
            val stringBuilder: mutable.StringBuilder = new StringBuilder()
                x._2._2.foreach(attribute =>
                    stringBuilder.append(attribute match {
                        case IndexAttribute() =>
                            table.indexColumnList.append(x._1)
                            " "
                        case _ => " " + attributeMappingToSQL(attribute)
                    })
                 )
            "`" + x._1 + "` " + x._2._1 + stringBuilder.result() + ",\n"
        ).mkString
    }

    val indexString = (indexColumnList: ListBuffer[String]) => s"INDEX(${indexColumnList.mkString})"
}
