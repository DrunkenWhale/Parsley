package com.parsley.connect


import com.parsley.connect.connection.{DataBaseConnection, MysqlConnection}

import java.sql.{Connection, DriverManager, Statement}

protected class DataBaseManager(databaseConnection: DataBaseConnection) {

    private val connection: Connection = connect()

    private def connect(): Connection = databaseConnection match {
        case mysqlConnection: MysqlConnection =>
            Class.forName("com.mysql.cj.jdbc.Driver")
            val connectURL = s"jdbc:mysql://${mysqlConnection.address}:${mysqlConnection.port}/${mysqlConnection.database}"
            DriverManager.getConnection(connectURL, mysqlConnection.user, mysqlConnection.password)
    }

}

object DataBaseManager {

    def register(databaseConnection: DataBaseConnection) = {
        this.dataBaseManager = new DataBaseManager(databaseConnection)
    }
    
    // singleton instance
    // not thread safe
    private var dataBaseManager: DataBaseManager = null

    
    private[parsley] def preparedStatement(sql:String) = this.dataBaseManager.connection.prepareStatement(sql)

}
