package com.parsley.connect.execute

import com.parsley.connect.DataBaseManager
import com.parsley.orm.DataToInstance.instanceFromParamSeq
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

private[parsley] object ExecuteSQL {
    def executeSQL(sql: String): Boolean = {
        DataBaseManager.preparedStatement(sql).execute
    }

    def executeUpdateSQL(sql: String): Int = {
        DataBaseManager.preparedStatement(sql).executeUpdate()
    }

    def executeQuerySQL[T <: Product](sql: String, columnType: Map[String, String])(implicit classTag: ClassTag[T]): List[T] = {
        val res = ListBuffer[T]()
        val resultSet = DataBaseManager.preparedStatement(sql).executeQuery()
        val columnTypeList = columnType.toList
        while (resultSet.next()) {
            val valueSeq: Seq[Any] = columnTypeList.map((name, tpe) => tpe match {
                case "String" => resultSet.getString(name)
                case "int" => resultSet.getInt(name)
                case "double" => resultSet.getDouble(name)
                case "long" => resultSet.getLong(name)
                case "float" => resultSet.getFloat(name)
                case "boolean" => resultSet.getBoolean(name)
                case "char" => resultSet.getByte(name)
            })
            res.append(instanceFromParamSeq[T](valueSeq))
        }
        res.toList
    }

    // use in insert method
    private[parsley] def executeInsertSQL(sql: String, seq: IndexedSeq[Any]): Unit = {
        val statment = DataBaseManager.preparedStatement(sql)
        seq.zipWithIndex.foreach((x, index) => { // why type become value class...
            x.getClass.getSimpleName match {
                case "Integer" => statment.setInt(index + 1, x.asInstanceOf[Int])
                case "Long" => statment.setLong(index + 1, x.asInstanceOf[Long])
                case "Float" => statment.setFloat(index + 1, x.asInstanceOf[Float])
                case "Double" => statment.setDouble(index + 1, x.asInstanceOf[Double])
                case "Boolean" => statment.setBoolean(index + 1, x.asInstanceOf[Boolean])
                case "String" => statment.setString(index + 1, x.asInstanceOf[String])
                case "Character" => statment.setByte(index + 1, x.asInstanceOf[Byte])
                case x => throw new Exception(s"unknown value type: $x")
            }
        })
        statment.execute()
    }
}
