package com.parsley.connect.execute

import com.parsley.connect.DataBaseManager

object ExecuteSQL {
    private[parsley] def executeSQL(sql: String): Unit = {
        DataBaseManager.preparedStatement(sql).execute
    }

    // use in insert method
    private[parsley] def executeSQLWithValueSeq(sql: String, seq: IndexedSeq[Any]): Unit = {
        val statment = DataBaseManager.preparedStatement(sql)
        seq.zipWithIndex.foreach((x, index) => {
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
