package com.parsley.v3.connect

import com.parsley.v3.connect.connection.{DataBaseConnection, MysqlConnection}

import java.sql.{Connection, DriverManager}

class DataBaseManager[T <: DataBaseConnection](databaseConnection: T) {

    private val connection: Connection = connect()

    private def connect(): Connection = databaseConnection match {
        case mysqlConnection: MysqlConnection =>
            Class.forName("com.mysql.cj.jdbc.Driver")
            val connectURL = s"jdbc:mysql://${mysqlConnection.address}:${mysqlConnection.port}/${mysqlConnection.database}"
            DriverManager.getConnection(connectURL, mysqlConnection.user, mysqlConnection.password)
    }

}

object DataBaseManager {

    private var dataBaseManager: DataBaseManager[DataBaseConnection] = null

    // singleton instance
    // not thread safe
    def apply[T <: DataBaseConnection](databaseConnection: T): DataBaseManager[T] = {
        if (dataBaseManager == null) {
            this.dataBaseManager = new DataBaseManager(databaseConnection)
            return dataBaseManager
        } else {
            dataBaseManager
        }
    }
    
}
