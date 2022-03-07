package com.parsley.logger

import java.text.SimpleDateFormat
import java.util.Date
import scala.util.Random


private[parsley] object Logger {
    def logginSQL(sql: String) = {
        // log implement
        println(s"\n${Console.CYAN}" +
            s"[ ${new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))} ]  " +
            s"${colorArray(Random.nextInt(6))} $sql ${Console.RESET}\n")
    }

    private final val colorArray = Array(
        "\u001b[32m"
        , "\u001b[33m"
        , "\u001b[34m"
        , "\u001b[35m"
        , "\u001b[36m"
        , "\u001b[37m"
    )

}
