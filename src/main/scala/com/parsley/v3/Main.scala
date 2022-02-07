package com.parsley.v3

import com.parsley.v3.connect.{DataBasseManager, MysqlConnection}

object Main {
    def main(args: Array[String]): Unit = {
        DataBasseManager.connect(MysqlConnection("test"))
    }
}
