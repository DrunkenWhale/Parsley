package com.parsley.orm

import com.parsley.connect.DataBaseManager
import com.parsley.connect.execute.ExecuteSQL
import com.parsley.logger.Logger
import com.parsley.orm.attribute.Attribute
import com.parsley.orm.curd.create.CreateImpl.createImpl
import com.parsley.orm.curd.query.QueryImpl.{queryImpl, queryRelationImpl}
import com.parsley.orm.curd.insert.InsertImpl.{insertImpl, insertRelationImpl}
import com.parsley.orm.curd.update.UpdateImpl.updateImpl
import com.parsley.orm.curd.delete.DeleteImpl.deleteImpl
import com.parsley.orm.Condition.*
import com.parsley.orm.curd.update.UpdateOperation

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

  private[parsley] val manyToManyTables: mutable.HashMap[Class[_], Table[_]] =
    mutable.HashMap.empty


  def create(): Unit = {
    createImpl(this)
  }

  def query(condition: Condition): List[T] = {
    queryImpl[T](this, condition)
  }

  def queryRelation[F <: Product](x: T)(implicit clsTag: ClassTag[F]): List[F] = {
    queryRelationImpl[T, F](this, x)
  }

  def insert(x: T): Unit = {
    insertImpl(this, x)
  }

  def insertRelation[F <: Product](x: T)(element: F)(implicit classTag: ClassTag[F]): Unit = {
    insertRelationImpl(this, x, element)
  }

  def update(updateOperation: UpdateOperation)(condition: Condition = Condition.*): Unit = {
    updateImpl(this, updateOperation, condition)
  }

  def delete(condition: Condition): Unit = {
    deleteImpl(this, condition)
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