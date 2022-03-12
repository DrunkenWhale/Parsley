package com.parsley.orm.curd.create

import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.attribute.Attribute
import com.parsley.orm.compile.DataToInstance
import com.parsley.orm.{DSL, Table, TypeMapping}

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

object CreateImpl {
  /* create */
  def createImpl(table: Table[_]): Unit = {

    val indexColumnList = ListBuffer[String]()
    val columnsSQL: String =
      table.columnType.map((name, tpe) => (s"`$name`", TypeMapping.scalaTypeMappingToSQLType(tpe), {
        val opt = table.columnAttribute.get(name)
        if (opt.isEmpty) {
          ""
        } else {
          val seq = opt.get
          if (seq.contains(DSL.indexed)) {
            indexColumnList.append(s"`$name`")
            seq.filter(x => x != DSL.indexed).map(x => x.sql).mkString(",")
          } else {
            seq.map(x => x.sql).mkString(",")
          }
        }
      })).map((name, tpe, attributes) => s"$name $tpe $attributes").mkString(",\n")

    val followRelationSQL =
      if (table.followingTables.nonEmpty) {
        "," + table.followingTables.toList
            .map((_, t) => s"`${t.name + "_" + table.name}` ${TypeMapping.scalaTypeMappingToSQLType(t.primaryKeyType)}")
            .mkString(",") + "\n"
      } else {
        ""
      }
    val indexedSQL =
      if (indexColumnList.nonEmpty) {
        s"INDEX(${indexColumnList.mkString(",")})\n"
      } else {
        ""
      }


    val sql = "\n" + s"CREATE TABLE IF NOT EXISTS `${table.name}` (\n" +
        columnsSQL + "\n" +
        followRelationSQL +
        indexedSQL +
        s");"

    val relationTableCreateSQL = if (table.manyToManyTables.nonEmpty) {
      table.manyToManyTables.map((clazz, relationTable) => {
        val relationTableName = if (relationTable.name > table.name) {
          s"${relationTable.name}_${table.name}"
        } else {
          s"${table.name}_${relationTable.name}"
        }
        "\n\n" + s"CREATE TABLE IF NOT EXISTS `$relationTableName`(\n" +
            s"`id` INT PRIMARY KEY,\n" +
            s"`${table.name}` ${TypeMapping.scalaTypeMappingToSQLType(table.primaryKeyType)},\n" +
            s"`${relationTable.name}` ${TypeMapping.scalaTypeMappingToSQLType(relationTable.primaryKeyType)}\n" +
            s");\n"
      }).mkString
    } else {
      ""
    }


    /*-----------------Logger--------------*/

    Logger.logginSQL(sql)
    Logger.logginSQL(relationTableCreateSQL)

    /*-------------------------------------*/

    ExecuteSQL.executeSQL(sql)
    ExecuteSQL.executeSQL(relationTableCreateSQL)
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
