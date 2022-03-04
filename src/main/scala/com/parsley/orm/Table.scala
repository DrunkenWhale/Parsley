package com.parsley.orm

import com.parsley.connect.DataBaseManager
import com.parsley.connect.execute.ExecuteSQL
import com.parsley.orm.attribute.Attribute

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

class Table[T <: Product](private[parsley] val name: String)(implicit clazzTag: ClassTag[T]) {

    // name,type
    private[parsley] lazy val primary: (String, String) = {
        val primaryKeyName = columnAttribute.filter((name, attribute) => attribute.contains(DSL.primaryKey)).head._1
        (name,columnType(primaryKeyName))
    }

    private[parsley] val clazz = clazzTag.runtimeClass

    // all columns name
    private[parsley] val columnName =
        clazz.getDeclaredConstructors.head.getParameters.map(x => x.getName)

    // column name map to its type
    private[parsley] val columnType: Map[String, String] =
        clazz.getDeclaredConstructors.head.getParameters
            .map(x => (x.getName, x.getType.getSimpleName)).toMap

    // column name map to its attribute
    private[parsley] val columnAttribute: mutable.HashMap[String, Seq[Attribute]] =
        mutable.HashMap.empty
    //
    //    def query(): T = {
    //
    //    }

    // 被映射的字段 : 本身表中用于join的字段的名字 => 主表主键的类型
    private[parsley] val followRelation: mutable.HashMap[String, String] =
        mutable.HashMap.empty

    // 从表名 => 从表中用于join的字段
    private[parsley] val mainRelation: mutable.HashMap[String, String] =
        mutable.HashMap.empty

}

protected object Table {

    def apply[T <: Product](implicit clazzTag: ClassTag[T]): Table[T] = new Table(name = clazzTag.runtimeClass.getSimpleName)

    def apply[T <: Product](name: String)(implicit clazzTag: ClassTag[T]): Table[T] = new Table(name)

    def putAttribute(table: Table[_])(seq: Seq[(String, Seq[Attribute])]): Unit = {
        seq.foreach((name, attributes) => {
            val opt = table.columnType.get(name)
            if (opt.isEmpty) {
                throw new Exception(s"$name is not a column in table: ${table.name}")
            } else {
                table.columnAttribute.put(name, attributes)
            }
        })
    }

}