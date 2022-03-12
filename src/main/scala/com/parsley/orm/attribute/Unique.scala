package com.parsley.orm.attribute

class Unique extends Attribute {

  override private[parsley] val code = 3

  override private[parsley] def sql = "UNIQUE"

}
