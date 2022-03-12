package com.parsley.orm.attribute

import com.parsley.orm.Table

class ManyToOne[T <: Product](mainTable: Table[T]) extends Attribute {
  private[parsley] val code: Int = 6

  private[parsley] def sql: String = {
    ???
  }
}
