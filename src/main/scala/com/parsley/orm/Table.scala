package com.parsley.orm

import scala.reflect.ClassTag
import com.parsley.macroImpl.primaryConstructorParamList

private sealed class Table[T](val name: String)(implicit clazzTag: ClassTag[T]) {

    private val columnTypeMap: Map[String, String] = primaryConstructorParamList[T].toMap

    private val columnExpressionList: collection.mutable.ListBuffer[ColumnExpression] =
        collection.mutable.ListBuffer.empty

    def create(): Unit = {

    }

    def query(): Unit = {

    }

    def save(): Unit = {

    }

    def update(): Unit = {

    }

    def delete(): Unit = {

    }


}

object Table {

    /**
     * if init table without table name
     * table name will be setted as class name of `T`
     *
     * @param: clazzTag => get generic type
     * */
    def apply[T]()(implicit clazzTag: ClassTag[T]): Table[T] =
        new Table[T](name = clazzTag.runtimeClass.getSimpleName)

    def apply[T](name: String)(implicit clazzTag: ClassTag[T]): Table[T] =
        new Table[T](name = name)

}
