package com.parsley.dsl

import com.parsley.connect.DataBaseManager
import com.parsley.connect.DataBaseManager.statment
import com.parsley.dsl.ColumnAttribute.attributeMappingToSQL
import com.parsley.dsl.Table.{createSQLString, loggingSQL}
import com.parsley.dsl.ColumnExpression.typeNameMappingToSQL

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.reflect.ClassTag

protected class Table[T](val tableName: String)(implicit classTag: ClassTag[T]) {

    private val clazz = classTag.runtimeClass

    // key: ColumnName
    // value: (ColumnType,ColumnAttributes)
    private val columnMap: mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]] =
    mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]](
        (clazz.getDeclaredFields.map(x => (x.getName -> (typeNameMappingToSQL(x.getType.getSimpleName), Seq[ColumnAttribute]())))): _*)

    def create(): Unit = {
        val sql = createSQLString(this)
        loggingSQL(sql,"create")
        DataBaseManager.statment().execute(sql)
    }

    def query(): Unit = {

    }

    def insert(obj: T): Unit = { // why i can't setAccessible ???
        // the val I defined will be compiled to method with the same name
        val objWithType = clazz.cast(obj)
        val columnList = columnMap.map(x => x._1).toList
        val middleSQLString: String =
            s"INSERT INTO `$tableName`\n" +
                s"${columnList.toString().substring(4)}\n" +
                s"VALUES\n" +
                s"(${"?," * columnList.size}"

        val sql = middleSQLString.substring(0, middleSQLString.length - 1) + ");"
        val statment = DataBaseManager.preparedStatement(sql)
        columnList.zipWithIndex.foreach((x, index) =>
            val columnMapElement = columnMap(x)
            val i = index + 1;
            columnMapElement._1 match {
                case "INT" => statment.setInt(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Int])
                case "BIGINT" => statment.setLong(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Long])
                case "FLOAT" => statment.setFloat(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Float])
                case "DOUBLE" => statment.setDouble(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Double])
                case "BOOLEAN" => statment.setBoolean(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Boolean])
                case "CHAR(255)" => statment.setString(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[String])
                case "CHAR(1)" => statment.setByte(i, clazz.getDeclaredMethod(x).invoke(objWithType).asInstanceOf[Byte])
                case x => throw Exception(s" type: $x not be implement")

            })
        loggingSQL(sql,"insert")
        statment.execute()
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

    private def loggingSQL(SQLString: String, SQLMethod: String): Unit = {
        // temp log
        println(s"\n----------------------------$SQLMethod------------------------------------\n")
        println(SQLString)
        println("\n----------------------------------------------------------------------\n\n")
    }

}
