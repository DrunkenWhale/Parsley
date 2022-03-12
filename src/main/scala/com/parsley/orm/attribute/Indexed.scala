package com.parsley.orm.attribute

class Indexed extends Attribute {

  override private[parsley] val code = 5

  override private[parsley] def sql = "INDEX"
}
