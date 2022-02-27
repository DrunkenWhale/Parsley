package com.parsley.orm

import scala.collection.mutable
import scala.reflect.ClassTag

private class Table[T <: Product](implicit clazzTag: ClassTag[T]) {

    private val clazz = clazzTag.runtimeClass
    private val name = clazz.getSimpleName
    // column name map to its type
    private val columnType: Map[String, String] =
        clazz.getDeclaredConstructors.head.getParameters
            .map(x => (x.getName, x.getType.getSimpleName)).toMap

    private val columnAttribute: mutable.HashMap[String, Seq[Attribute]] = mutable.HashMap.empty

    def create(): Unit = {

    }

}

protected object Table {
    def apply[T <: Product](implicit clazzTag: ClassTag[T]): Table[T] = new Table

    def putAttribute(table: Table[_])(seq: Seq[(String, Seq[Attribute])]): Unit = {
        seq.foreach((name, attributes) => {
            val opt = table.columnType.get(name)
            if (opt.isEmpty) {
                throw new Exception(s"$name is not a column in table: ${table.name}")
            } else {
                table.columnAttribute.put(name, attributes)
            }
        })
        println(table.columnAttribute)
    }

}