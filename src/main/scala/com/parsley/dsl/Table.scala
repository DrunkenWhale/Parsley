package com.parsley.dsl

import com.parsley.connect.DataBaseManager
import com.parsley.dsl.ColumnAttribute.attributeMappingToSQL
import com.parsley.dsl.Table.{createSQLString, insertSQLString, loggingSQL, setInsertPreparedmentValue}
import com.parsley.dsl.ColumnExpression.typeNameMappingToSQL
import com.parsley.util.realInstance

import java.sql.PreparedStatement
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
        val sqlString = createSQLString(this.tableName, this.columnMap)
        loggingSQL(sqlString, "create")
        DataBaseManager.statment().execute(sqlString)
    }

    def query(): Unit = {
        val queryColumnMiddleString = this.columnMap.map(x => x._1).toString().substring(12)
        val queryColumnString = queryColumnMiddleString.substring(0, queryColumnMiddleString.length - 1)
        println(queryColumnString)
        val result = DataBaseManager.statment().executeQuery(s"SELECT $queryColumnString FROM ${this.tableName};")
        val resultValueList = ListBuffer[Seq[Any]]()
        while (result.next()) {
            resultValueList.append(
                columnMap.map(x => x._2._1).zipWithIndex.map((x, index) => {
                val i = index + 1
                x match {
                    case "INT" => result.getInt(i)
                    case "BIGINT" => result.getLong(i)
                    case "FLOAT" => result.getFloat(i)
                    case "DOUBLE" => result.getDouble(i)
                    case "BOOLEAN" => result.getBoolean(i)
                    case "CHAR(255)" => result.getString(i)
                    case "CHAR(1)" => result.getByte(i).toChar
                    case x => throw Exception(s" type: $x not be implement")
                }
            }).toSeq
            )
        }

        val t = resultValueList.map(x=>realInstance[T](x)).toList
//        println(res)
        println(t)
    }

    def insert(obj: T): Unit = { // why i can't setAccessible ???
        // the val I defined will be compiled to method with the same name
        val sqlString = insertSQLString(tableName, columnMap, clazz)
        val preparedStatement = DataBaseManager.preparedStatement(sqlString)
        setInsertPreparedmentValue(preparedStatement)(obj, columnMap, clazz)
        loggingSQL(preparedStatement.toString.substring(43), "insert")
        preparedStatement.execute()
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


    //--------------------------------- create --------------------------------------------------------/

    def createSQLString(tableName: String, columnMap: mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]) = {
        s"CREATE TABLE IF NOT EXISTS `${tableName}`(\n" +
            s"${columnString(columnMap)}" + ");"
    }

    private def columnString(columnMap: mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]]) = {
        var indexColumnList = List[String]()
        val middleSQLString = columnMap.toList.map(x =>
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
        ).mkString

        if (indexColumnList.isEmpty) {
            middleSQLString.substring(0, middleSQLString.length - 2) + "\n"
        } else {
            middleSQLString + s"${indexString(indexColumnList)}" + "\n"
        }

    }

    private def indexString(indexColumnList: List[String]) = {
        val middleString = indexColumnList.map(x => x + ",").mkString
        if (middleString.length == 0) {
            "INDEX()"
        } else {
            s"INDEX(${middleString.substring(0, middleString.length - 1)})"
        }

    }

    //---------------------------------- insert --------------------------------------------------------/

    def insertSQLString(tableName: String, columnMap: mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]], clazz: Class[_]) = {

        val columnList = columnMap.map(x => x._1).toList

        val middleSQLString: String =
            s"INSERT INTO `$tableName`" +
                s"${columnList.toString().substring(4)}" +
                s" VALUES " +
                s"(${"?," * columnList.size}"

        middleSQLString.substring(0, middleSQLString.length - 1) + ");"
    }

    private def setInsertPreparedmentValue(preparedStatement: PreparedStatement)
                                          (obj: Any, columnMap: mutable.HashMap[String, Tuple2[String, Seq[ColumnAttribute]]], clazz: Class[_])
    : Unit = {

        columnMap.map(x => x._1).zipWithIndex.foreach((x, index) =>
            val columnMapElement = columnMap(x)
            val i = index + 1;

            columnMapElement._1 match {
                case "INT" => preparedStatement.setInt(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Int])
                case "BIGINT" => preparedStatement.setLong(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Long])
                case "FLOAT" => preparedStatement.setFloat(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Float])
                case "DOUBLE" => preparedStatement.setDouble(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Double])
                case "BOOLEAN" => preparedStatement.setBoolean(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Boolean])
                case "CHAR(255)" => preparedStatement.setString(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[String])
                case "CHAR(1)" => preparedStatement.setByte(i, clazz.getDeclaredMethod(x).invoke(obj).asInstanceOf[Byte])
                case x => throw Exception(s" type: $x not be implement")
            })
    }


    private def loggingSQL(SQLString: String, SQLMethod: String): Unit = {
        // temp log
        val sqlStringColor = Console.BOLD + (SQLMethod match {
            case "create" => Console.YELLOW
            case "query" => Console.MAGENTA
            case "insert" => Console.BLUE
            case "update" => Console.GREEN
            case "delete" => Console.RED
        })
        println(Console.CYAN + s"\n----------------------------$SQLMethod------------------------------------\n")
        println(sqlStringColor + SQLString)
        println(Console.CYAN + "\n----------------------------------------------------------------------\n")
    }

}
