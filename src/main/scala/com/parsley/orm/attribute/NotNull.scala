package com.parsley.orm.attribute

class NotNull extends Attribute {
  override private[parsley] val code = 4

  override private[parsley] def sql = "NOT NULL"
}
