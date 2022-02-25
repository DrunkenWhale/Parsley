package com.parsley.orm

import com.parsley.macroImpl.{nameOf, primaryConstructorParamList}

inline def create[T](columnExpressionSeq: Seq[ColumnExpression]): String = {

    val columnNameTypeList: List[(String, String)] =
        primaryConstructorParamList[T]

    val columnNameToAttributeMap: Map[String, Seq[Attribute]] =
        columnExpressionSeq.map(x => (x.name, x.attribute)).toMap

    val createTableSQLBody: String =
        columnNameTypeList.map((name, tpe) => {
            val opt = columnNameToAttributeMap.get(name)
            if (!opt.isEmpty) {
                ColumnExpression(name, tpe, opt.get)
            } else {
                ColumnExpression(name, tpe, Seq()) // this column with out attribute
            }
        }.generateSQLSentence()
        ).mkString(",\n")

    val indexList: Seq[String] =
        columnExpressionSeq.filter(x => x.isIndexColumn()).map(x => x.name)

    val createTableSQLIndex: String =
        if (indexList.isEmpty) {
            " "
        } else {
            ",\n" + indexList.mkString(",  ")
        }
    //    val createTableSQL =
    s"CREATE TABLE `${nameOf[T]}`(\n" +
        s"$createTableSQLBody " +
        s"$createTableSQLIndex\n" +
        s");"
}

inline def insert[T](): Unit = {

}

inline def query[T](): Unit = {

}

inline def update[T](): Unit = {

}

inline def delete[T](): Unit = {

}
