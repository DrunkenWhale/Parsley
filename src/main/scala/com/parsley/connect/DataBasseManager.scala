package com.parsley.connect


import com.parsley.connect.connection.{DataBaseConnection, MysqlConnection}

import java.sql.{Connection, DriverManager}

class DataBaseManager(databaseConnection: DataBaseConnection) {

    private val connection: Connection = connect()

    private def connect(): Connection = databaseConnection match {
        case mysqlConnection: MysqlConnection =>
            Class.forName("com.mysql.cj.jdbc.Driver")
            val connectURL = s"jdbc:mysql://${mysqlConnection.address}:${mysqlConnection.port}/${mysqlConnection.database}"
            DriverManager.getConnection(connectURL, mysqlConnection.user, mysqlConnection.password)
    }

}

object DataBaseManager {

    private var dataBaseManager: DataBaseManager | Null = null

    // singleton instance
    // not thread safe
    def apply(databaseConnection: DataBaseConnection): DataBaseManager = {
        if (dataBaseManager == null) {
            this.dataBaseManager = new DataBaseManager(databaseConnection)
            return dataBaseManager
        } else {
            return dataBaseManager
        }
    }

}
