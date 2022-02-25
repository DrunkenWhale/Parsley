package com.parsley.orm.curd

import com.parsley.orm.{Attribute, ColumnExpression}
import com.parsley.macroImpl.{nameOf, primaryConstructorParamList}

inline def createImpl[T](columnNameAttributeSeq: Seq[(String, Seq[Attribute])]): String = {

    val columnNameTypeList: List[(String, String)] =
        primaryConstructorParamList[T]

    val columnNameToAttributeMap: Map[String, Seq[Attribute]] =
        columnNameAttributeSeq.toMap

    val columnExpressionSeq: Seq[ColumnExpression] =
        columnNameTypeList.map((name, tpe) => {
            val opt = columnNameToAttributeMap.get(name)
            if (!opt.isEmpty) {
                ColumnExpression(name, tpe, opt.get)
            } else {
                ColumnExpression(name, tpe, Seq()) // this column with out attribute
            }
        })

    val createTableSQLBody: String =
        columnExpressionSeq.map(x => x.generateSQLSentence()).mkString(",\n")

    val indexList: Seq[String] =
        columnExpressionSeq.filter(x => x.isIndexColumn()).map(x => x.name)

    val createTableSQLIndex: String =
        if (indexList.isEmpty) {
            ""
        } else {
            ",\nINDEX(" + indexList.mkString(",  ") + ")"
        }
    s"CREATE TABLE `${nameOf[T]}`(\n" +
        s"$createTableSQLBody" +
        s"$createTableSQLIndex\n" +
        s");"
}