package com.parsley.v3

import com.parsley.v3.connect.DataBaseManager
import com.parsley.v3.connect.connection.MysqlConnection

object Main {
    def main(args: Array[String]): Unit = {
        DataBaseManager(MysqlConnection("test"))
    }
}
