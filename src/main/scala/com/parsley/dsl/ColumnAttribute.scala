package com.parsley.dsl

sealed trait ColumnAttribute {}

protected sealed case class PrimaryKeyAttribute() extends ColumnAttribute

protected sealed case class AutoIncrementAttribute() extends ColumnAttribute

protected sealed case class IndexAttribute() extends ColumnAttribute

protected sealed case class UniqueAttribute() extends ColumnAttribute

protected sealed case class NullableAttribute() extends ColumnAttribute
