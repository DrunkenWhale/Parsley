package com.parsley.connect.execute

import com.parsley.connect.DataBaseManager

object ExecuteSQL {
    private[parsley] def executeSQL(sql:String): Unit ={
        DataBaseManager.preparedStatement(sql).execute
    } 
}
