package com.parsley.v3.connect

import java.sql.{Connection, DriverManager}

class DataBasseManager[T <: DataBaseConnection](databaseConnection: T) {

    private val connection: Connection = connect()

    private def connect(): Connection = {
        databaseConnection.getClass.getSimpleName match {
            case "MysqlConnection" =>
                val mysqlConnection = connection.asInstanceOf[MysqlConnection]
                Class.forName("com.mysql.cj.jdbc.Driver")
                val connectURL = s"jdbc:mysql://${mysqlConnection.address}:${mysqlConnection.port}/${mysqlConnection.database}"
                DriverManager.getConnection(connectURL, mysqlConnection.user, mysqlConnection.password)
        }
    }

}
