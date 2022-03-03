package com.parsley.logger

import scala.util.Random


private[parsley] object Logger {
    def logginSQL(sql: String) = {
        // log implement
        println("\n" + colorArray(Random.nextInt(8)) + sql + Console.RESET + "\n")
    }

    private final val colorArray = Array(
        "\u001b[30m"
        , "\u001b[31m"
        , "\u001b[32m"
        , "\u001b[33m"
        , "\u001b[34m"
        , "\u001b[35m"
        , "\u001b[36m"
        , "\u001b[37m"
    )


}
