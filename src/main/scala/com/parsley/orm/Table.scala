package com.parsley.orm

import com.parsley.macroImpl.{nameOf, primaryConstructorParamList}
import com.parsley.orm.curd.createImpl

inline def create[T](columnNameAttributeSeq: Seq[(String, Seq[Attribute])): Unit ={
    createImpl[T](columnNameAttributeSeq)
}

inline def insert[T](): Unit = {

}

inline def query[T](): Unit = {

}

inline def update[T](): Unit = {

}

inline def delete[T](): Unit = {

}
