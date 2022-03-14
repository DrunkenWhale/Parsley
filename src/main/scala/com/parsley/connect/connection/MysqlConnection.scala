package com.parsley.connect.connection

case class MysqlConnection(database: String,
                           user: String = "root",
                           password: String = "",
                           address: String = "localhost",
                           port: Int = 3306)
    extends DataBaseConnection {
}
