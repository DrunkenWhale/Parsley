package com.parsley.orm

import scala.reflect.ClassTag
import com.parsley.macroImpl.primaryConstructorParamList

import scala.collection.mutable
import scala.quoted.{Expr, Quotes, Type}


private sealed class Table[T](val name: String)(implicit clazzTag: ClassTag[T]) {

    // mapping :   name => type(scala type(as String))
//    private val columnTypeMap: Map[String, String] = primaryConstructorParamList[T].toMap
    private val columnTypeMap: Map[String, String] = List().toMap

    // when lack declare method on table,`columnExpressionMap.attributes` will be init as empty Seq
    private val columnExpressionMap: mutable.HashMap[String, ColumnExpression] =
        mutable.HashMap.empty.addAll(
            columnTypeMap.map((name, tpe) =>
                (name, ColumnExpression(name, ColumnExpression.typeMappingToSQL(tpe), Seq()))
            )
        )

    def create(): Unit = {
        println("-----------------------------")
        println(name)
        println(this.columnTypeMap)
        println(this.columnExpressionMap)
        println(this.columnExpressionMap.map((_,columnExpression)=>columnExpression.generateSQLSentence()).mkString(",\n"))
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
    inline def apply[T]()(implicit clazzTag: ClassTag[T]): Table[T] =
        new Table[T](name = clazzTag.runtimeClass.getSimpleName)

    inline def apply[T](name: String)(implicit clazzTag: ClassTag[T]): Table[T] =
        new Table[T](name = name)

    // side effect!
    // only call this method when declare table method exist
    def addAttributesToColumnExpressionMap(map: mutable.HashMap[String, ColumnExpression])
                                          (columnExpressionWithAttributeList: Seq[ColumnExpression]): Unit = {
        columnExpressionWithAttributeList.foreach(
            x => {
                val columnExpressionWithoutAttributesOption = map.get(x.name)
                if (columnExpressionWithoutAttributesOption.isEmpty) {
                    // when type `T` has not any fields called `x.name`
                    throw new Exception(s"${x.name} unexist in its class's primary constructor")
                } else {
                    map.put(x.name, ColumnExpression(x.name, columnExpressionWithoutAttributesOption.get.tpe, x.attribute))
                }
            }
        )
    }

}
