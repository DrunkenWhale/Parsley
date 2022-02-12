package com.parsley.dsl

sealed trait ColumnAttribute {}

sealed case class PrimaryKeyAttribute() extends ColumnAttribute

sealed case class AutoIncrementAttribute() extends ColumnAttribute

sealed case class IndexAttribute() extends ColumnAttribute

sealed case class UniqueAttribute() extends ColumnAttribute

sealed case class NullableAttribute() extends ColumnAttribute
