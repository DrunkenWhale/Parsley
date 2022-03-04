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

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.util.control.Breaks.break

class Table[T <: Product](private[parsley] val name: String)(implicit clazzTag: ClassTag[T]) {

    // name,type
    private[parsley] lazy val primary: (String, String) = {
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

    private[parsley] val followingTables: mutable.ListBuffer[Table[_]] =
        mutable.ListBuffer.empty

    private[parsley] val followedTables: mutable.ListBuffer[Table[_]] =
        mutable.ListBuffer.empty

    def create(): Unit = {
        createImpl(this)
    }

    def query(condition: Condition): List[T] = {
        queryImpl(condition) from this
    }

    def queryRelation[F <: Product](tb: Table[F])(x: T)(implicit clsTag: ClassTag[F]):List[F] = {
        var value: Any = null
        for (i <- 0 until x.productArity) {
            if (x.productElementName(i) == this.primary._1) {
                value = x.productElement(i)
                // break
                // this control syntax......
                // ... must be changed
            }
        }
        val followedTableJoinColumnName = s"${this.name}_${tb.name}"
        val columnNameString = tb.columnName.map(x => s"`${tb.name}`.`$x`").mkString(",")
        val sql =
            s"SELECT $columnNameString" +
                s" FROM `${tb.name}`" +
                s" JOIN `${this.name}`" +
                s" ON `${tb.name}`.`${followedTableJoinColumnName}`=`${this.name}`.`${primary._1}`" +
                s" WHERE `${this.name}`.`${this.primary._1}`='$value';"
        Logger.logginSQL(sql)
        ExecuteSQL.executeQuerySQL[F](sql, tb.columnType)
    }

    def insert(x: T): Unit = {
        insertImpl(x) in this
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

}