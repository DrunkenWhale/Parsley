package com.parsley.orm

import com.parsley.connect.DataBaseManager
import com.parsley.connect.execute.ExecuteSQL

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
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

        val indexColumnList = ListBuffer[String]()
        val columnsSQL: String = columnType.map((name, tpe) => (s"`$name`", TypeMapping.scalaTypeMappingToSQLType(tpe), {
            val opt = columnAttribute.get(name)
            if (opt.isEmpty) {
                ""
            } else {
                val seq = opt.get
                if (seq.contains(Attribute.Indexed)) {
                    indexColumnList.append(s"`$name`")
                    seq.filter(x => x != Attribute.Indexed).map(x => x.SQL).mkString(",")
                } else {
                    seq.map(x => x.SQL).mkString(",")
                }
            }
        })).map((name, tpe, attributes) => s"$name $tpe $attributes").mkString(",\n")

        val indexedSQL =
            if (indexColumnList.length > 0) {
                s"INDEX(${indexColumnList.mkString(",")})\n"
            } else {
                ""
            }
        val sql = s"CREATE TABLE IF NOT EXISTS `$name` (\n" +
            columnsSQL + "\n" +
            indexedSQL +
            s");"


        ExecuteSQL.executeSQL(sql)
    }

    def insert(x: T): Unit = {
        val element = x.asInstanceOf[Tuple]
        val elementLength = element.productArity
        val elementNameValueSeq =
            for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))
        val elementNameListString = elementNameValueSeq.map((name,_)=>name).mkString(",")
        val elementValueList = elementNameValueSeq.map((_,value)=>value)
        val sql = s"INSERT INTO `$name` $elementNameListString"
        println(sql)
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
    }

}