package com.parsley.dsl

import com.parsley.connect.DataBaseManager
import com.parsley.connect.DataBaseManager.statment
import com.parsley.dsl.ColumnAttribute.attributeMappingToSQL
import com.parsley.dsl.Table.createSQLString

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.reflect.ClassTag

protected class Table[T](val tableName: String)(private val classTag: ClassTag[T]) {

    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    private val columnMap = mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]()



    def create(): Unit = {
        val sql = createSQLString(this)
        println(sql)
        DataBaseManager.statment().execute(sql)
    }

    def query(): Unit = {

    }

    def insert(x: T): Unit = {
        val sql: String = s" INSERT INTO $tableName \n VALUES "
        DataBaseManager.statment().execute("")
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
            s"${columnString(table)}" + ");"

    }

    val columnString: Table[_] => String = (table: Table[_]) => {
        var indexColumnList = List[String]()
        table.columnMap.toList.map(x =>
            val stringBuilder: mutable.StringBuilder = new StringBuilder()
                x
            ._2._2.foreach(attribute =>
            stringBuilder.append(attribute match {
                case IndexAttribute() =>
                    indexColumnList = indexColumnList.appended(x._1)
                    " "
                case _ => " " + attributeMappingToSQL(attribute)
            })
        )
            "`" + x._1 + "` " + x._2._1 + stringBuilder.result() + ",\n"
        ).mkString + s"${indexString(indexColumnList)}" + "\n"
    }

    val indexString = (indexColumnList: List[String]) => {
        val middleString = indexColumnList.map(x => x + ",").mkString
        if (middleString.length == 0) {
            ""
        } else {
            s"INDEX(${middleString.substring(0, middleString.length - 1)})"
        }

    }
}
