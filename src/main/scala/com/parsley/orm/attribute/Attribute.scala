package com.parsley.orm.attribute

/**
 * code mapping
 *
 * 1 => PrimaryKey
 * 2 => AutoIncrement
 * 3 => Unique
 * 4 => NotNull
 * 5 => Indexed
 * 6 => ManyToOne
 * */

trait Attribute {
  private[parsley] val code: Int // unique indentify

  private[parsley] def sql: String

  private[parsley] def unapply(arg: Attribute): Int = code

  override def toString: String = sql
}



