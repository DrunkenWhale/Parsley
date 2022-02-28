package com.parsley.orm

import com.parsley.connect.execute.ExecuteSQL
import sourcecode.Text

import java.lang.reflect.Constructor
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

object DSL {

    def table[T <: Product](implicit classTag: ClassTag[T]) = Table.apply[T]

    def declare(columnNameWithAttribute: (String, Seq[Attribute])*): Seq[(String, Seq[Attribute])] = {
        columnNameWithAttribute
    }

    /**
     * side effect!
     * */
    def on[T <: Product](table: Table[T])
                        (operation: (T => Seq[(String, Seq[Attribute])]))
                        (implicit classTag: ClassTag[T]): Unit = {
        val nameWithAttributeSeq = operation(DataToInstance.instanceFromFakeParamSeq[T])
        //change table's map
        Table.putAttribute(table)(nameWithAttributeSeq)
    }

    extension (self:
               sourcecode.Text[String]
                   | sourcecode.Text[Int]
                   | sourcecode.Text[Long]
                   | sourcecode.Text[Double]
                   | sourcecode.Text[Float]
                   | sourcecode.Text[Boolean]) {
        def is(attribute: Attribute*): (String, Seq[Attribute]) = {
            (self.source.split("\\.").last, attribute)
        }
    }

    // unuseful method
    // for the interest
    // for example:
    // you can use insert(?)(table) or insert(?) in table
    // they have the same result
    extension[T <: Product] (self: Table[T] => _) {
        def in(table: Table[T])(implicit classTag: ClassTag[T]): Unit = {
            self(table)
        }
    }

    def create(table: Table[_]): Unit = {

        val indexColumnList = ListBuffer[String]()
        val columnsSQL: String = table.columnType.map((name, tpe) => (s"`$name`", TypeMapping.scalaTypeMappingToSQLType(tpe), {
            val opt = table.columnAttribute.get(name)
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
        val sql = s"CREATE TABLE IF NOT EXISTS `${table.name}` (\n" +
            columnsSQL + "\n" +
            indexedSQL +
            s");"
        // log
        ExecuteSQL.executeSQL(sql)
    }

    def insert[T <: Product](x: T)(table: Table[T]): Unit = {
        val element = x.asInstanceOf[Tuple]
        val elementLength = element.productArity
        val elementNameValueSeq =
            for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))
        val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")
        val elementValueList: IndexedSeq[Any] = elementNameValueSeq.map((_, value) => value)
        val sql = s"INSERT INTO `${table.name}` ($elementNameListString) VALUES (${List.fill(elementLength)("?").mkString(",")})"
        // log
        ExecuteSQL.executeSQLWithValueSeq(sql, elementValueList)

    }

    def query[T <: Product](condition:Condition=new Condition)(table: Table[T])(implicit classTag: ClassTag[T]): Unit = {
        val columnNameString = table.columnName.map(x=>"`"+x+"`").mkString(",")
        val sql = s"SELECT $columnNameString FROM `${table.name}` ${condition}"

        println(sql)
    }
}
