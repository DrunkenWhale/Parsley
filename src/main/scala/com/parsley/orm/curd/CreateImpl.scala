package com.parsley.orm.curd

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.{Attribute, DataToInstance, Table, TypeMapping}

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

object CreateImpl {
    /* create */
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
        
        /*-----------------Logger--------------*/
        
        Logger.logginSQL(sql)
        
        /*-------------------------------------*/
        
        ExecuteSQL.executeSQL(sql)
    }

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
}
