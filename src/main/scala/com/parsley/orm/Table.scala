package com.parsley.orm

import com.parsley.macroImpl.{nameOf, primaryConstructorParamList}
import com.parsley.orm.curd.createImpl

inline def create[T](columnNameAttributeSeq: Seq[(String, Seq[Attribute])]): Unit = {
    val createSQL = createImpl[T](columnNameAttributeSeq)
    // transaction with database
}

inline def insert[T](x: T): Unit = {
    val columnExpressionList = primaryConstructorParamList[T].map((name, tpe) => ColumnExpression(name, tpe, Seq()))
    println(columnExpressionList)
}

inline def query[T](): Unit = {

}

inline def update[T](): Unit = {

}

inline def delete[T](): Unit = {

}
