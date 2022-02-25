package com.parsley.orm

import com.parsley.macroImpl.primaryConstructorParamMap

sealed trait Table {

    def create[T](columnExpressionSeq: Seq[ColumnExpression]): Unit = {
        val columnNameToTypeMap = primaryConstructorParamMap[T]
        val createTableSQLBody = columnExpressionSeq.map(x =>
            ColumnExpression(x.name, columnNameToTypeMap(ColumnExpression.typeMappingToSQL(x.name)), x.attribute))
            .mkString(",\n")
        val indexList = columnExpressionSeq.filter(x => x.isIndexColumn())
        val createTableIndexSQL = if (indexList.isEmpty) {
            " "
        } else {
            ",\n" + indexList.mkString(",  ")
        }
        val createTableSQL = s"CREATE TABLE $"
    }

    inline def insert[T](): Unit = {

    }

    inline def query[T](): Unit = {

    }

    inline def update[T](): Unit = {

    }

    inline def delete[T](): Unit = {

    }
}
