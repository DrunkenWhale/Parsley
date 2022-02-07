package com.parsley.v3.connect

case class MysqlConnection(val database: String,
                           val user: String = "root",
                           val password: String = "",
                           val address: String = "localhost",
                           val port: Int = 3306
                          )
    extends DataBaseConnection {
}
