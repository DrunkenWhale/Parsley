package com.parsley.connect.execute

import com.parsley.connect.DataBaseManager

object ExecuteSQL {
    private[parsley] def executeSQL(sql: String): Unit = {
        DataBaseManager.preparedStatement(sql).execute
    }

    // use on insert method
    private[parsley] def executeSQLWithValueSeq(sql: String, seq: IndexedSeq[Any]): Unit = {
        val statment = DataBaseManager.preparedStatement(sql)
        seq.zipWithIndex.foreach((x, index) => {
            x.getClass.getSimpleName match {
                case "int" => statment.setInt(index + 1, x.asInstanceOf[Int])
                case "long" => statment.setLong(index + 1, x.asInstanceOf[Long])
                case "float" => statment.setFloat(index + 1, x.asInstanceOf[Float])
                case "double" => statment.setDouble(index + 1, x.asInstanceOf[Double])
                case "boolean" => statment.setBoolean(index + 1, x.asInstanceOf[Boolean])
                case "String" => statment.setByte(index + 1, x.asInstanceOf[Byte])
                case "char" => statment.setString(index + 1, x.asInstanceOf[String])
                case x => throw new Exception(s"unknown value type: $x")
            }
        })
        statment.execute()
    }
}
