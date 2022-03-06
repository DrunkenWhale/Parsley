package com.parsley.orm

import com.parsley.connect.DataBaseManager
import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.attribute.Attribute
import com.parsley.orm.curd.CreateImpl.create as createImpl
import com.parsley.orm.curd.QueryImpl.{from, query as queryImpl}
import com.parsley.orm.curd.InsertImpl.{in, insert as insertImpl}
import com.parsley.orm.curd.UpdateImpl.{into, where, update as updateImpl}
import com.parsley.orm.curd.DeleteImpl.delete as deleteImpl
import com.parsley.orm.Condition.*
import com.parsley.orm.Table.findFieldValueFromClassByName

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

class Table[T <: Product](private[parsley] val name: String)(implicit clazzTag: ClassTag[T]) {

    // name,type
    private[parsley] lazy val (primaryKeyName, primaryKeyType): (String, String) = {
        val primaryKeyName = columnAttribute.filter((name, attribute) => attribute.contains(DSL.primaryKey)).head._1
        (primaryKeyName, columnType(primaryKeyName))
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

    private[parsley] val followingTables: mutable.HashMap[Class[_], Table[_]] =
        mutable.HashMap.empty

    private[parsley] val followedTables: mutable.HashMap[Class[_], Table[_]] =
        mutable.HashMap.empty

    def create(): Unit = {
        createImpl(this)
    }

    def query(condition: Condition): List[T] = {
        queryImpl(condition) from this
    }

    def queryRelation[F <: Product](x: T)(implicit clsTag: ClassTag[F]): List[F] = {
        val tb = this.followedTables(clsTag.runtimeClass)
        var value: Any = findFieldValueFromClassByName(x, this.primaryKeyName)
        val followedTableJoinColumnName = s"${this.name}_${tb.name}"
        val columnNameString = tb.columnName.map(x => s"`${tb.name}`.`$x`").mkString(",")
        val sql =
            s"SELECT $columnNameString" +
                s" FROM `${tb.name}`" +
                s" JOIN `${this.name}`" +
                s" ON `${tb.name}`.`${followedTableJoinColumnName}`=`${this.name}`.`${this.primaryKeyName}`" +
                s" WHERE `${this.name}`.`${this.primaryKeyName}`='$value';"
        Logger.logginSQL(sql)
        ExecuteSQL.executeQuerySQL[F](sql, tb.columnType)
    }

    def insert(x: T): Unit = {
        insertImpl(x) in this
    }

    def insertRelation[F <: Product](x: T)(element: F)(implicit classTag: ClassTag[F]): Unit = {
        val tb = this.followedTables(classTag.runtimeClass).asInstanceOf[Table[F]]

        val relationColumnValue = findFieldValueFromClassByName(x, this.primaryKeyName)

        val elementLength = element.productArity

        val elementNameValueSeq =
            for (i <- 0 until elementLength) yield (element.productElementName(i), element.productElement(i))

        val elementNameListString = elementNameValueSeq.map((name, _) => "`" + name + "`").mkString(",")

        val elementValueList: IndexedSeq[Any] = elementNameValueSeq.map((_, value) => value)

        val columnRelation = s"`${this.name}_${tb.name}`"

        val sql = s"INSERT INTO `${tb.name}` ($elementNameListString,$columnRelation) " +
            s"VALUES (${List.fill(elementLength + 1)("?").mkString(",")})"
        /*-----------------Logger--------------*/

        Logger.logginSQL(sql)

        /*-------------------------------------*/
        ExecuteSQL.executeInsertSQL(sql, elementValueList.appended(relationColumnValue))
    }

    def update(updateOperation: UpdateOperation)(condition: Condition = Condition.*): Unit = {
        updateImpl(updateOperation) where (condition) into this
    }

    def delete(condition: Condition): Unit = {
        deleteImpl(condition)(this)
    }

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

    /**
     * foreach cls's fields
     * get value from special field by field name
     *
     * @param: cls : class which you want to find fields value
     * @param: name: field name
     * @return: field's value, unknown type
     * @throws: throw a exception when can't find field by name
     *
     * */
    private def findFieldValueFromClassByName[F <: Product](cls: F, name: String): Any = {
        val size = cls.productArity
        for (i <- 0 to size) {
            if (cls.productElementName(i) == name) {
                return cls.productElement(i)
            }
        }
        throw new Exception(s"can't find $name field from $cls")
    }


}